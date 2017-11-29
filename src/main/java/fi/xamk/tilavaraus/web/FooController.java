package fi.xamk.tilavaraus.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.xamk.tilavaraus.domain.*;
import fi.xamk.tilavaraus.domain.validation.ReservationValidator;
import fi.xamk.tilavaraus.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class FooController {

	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;
	private final ObjectMapper objectMapper;
	private final ReservationValidator reservationValidator;
	private final ReservationService reservationService;

	@PostMapping("/rooms/{id}")
	@PreAuthorize("(principal.username == #reservation.user.email)")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String checkout(@Valid @ModelAttribute("reservation") Reservation reservation,
	                       BindingResult bindingResult,
	                       @AuthenticationPrincipal MyUserDetails userDetails,
	                       Model model,
	                       HttpServletRequest request,
	                       @PathVariable("id") String roomId) throws JsonProcessingException {
		model.addAttribute("user", userDetails.getUser());
		if (bindingResult.hasErrors()) {
			model.addAttribute("room", reservation.getRoom());
			model.addAttribute("eventsJson", objectMapper.writeValueAsString(getEvents(reservation.getRoom(), request)));
			return "detail";
		}
		return "checkout";
	}

	@Autowired
	public FooController(RoomRepository roomRepository,
	                     ReservationRepository reservationRepository,
	                     ObjectMapper objectMapper,
	                     ReservationValidator reservationValidator,
	                     ReservationService reservationService) {
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
		this.objectMapper = objectMapper;
		this.reservationValidator = reservationValidator;
		this.reservationService = reservationService;
	}

	@GetMapping("/reservations/{id}/cancel")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PreAuthorize("(principal.username == #reservation.user.email) && #reservation.cancellable")
	public String cancelReservation(@PathVariable("id") Reservation reservation) {
		reservationRepository.delete(reservation);
		return "redirect:/myreservations";
	}

	@GetMapping("/rooms/{id}/events")
	@ResponseBody
	public Iterable<FullCalendarEvent> getEvents(@PathVariable("id") Room room, HttpServletRequest httpServletRequest) {
		return reservationRepository.findByRoom(room).stream()
			.map(reservation -> new FullCalendarEvent(reservation.getId().toString(),
				httpServletRequest.isUserInRole("ADMIN") ? reservation.getUser().getEmail() : "",
				LocalDateTime.of(reservation.getDate(), reservation.getStartTime()),
				LocalDateTime.of(reservation.getDate(), reservation.getEndTime())))
			.collect(Collectors.toList());
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "index";
	}

	@GetMapping("/myreservations")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String myReservations(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		List<Reservation> all = reservationRepository.findByUser(myUserDetails.getUser());
		List<Reservation> newReservations = all.stream().filter(r -> !r.isOld()).collect(Collectors.toList());
		List<Reservation> oldReservations = all.stream().filter(Reservation::isOld).collect(Collectors.toList());
		model.addAttribute("newReservations", newReservations);
		model.addAttribute("oldReservations", oldReservations);
		return "myreservations";
	}

	@InitBinder("reservation")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(reservationValidator);
	}

	@PostMapping("/reserve")
	@PreAuthorize("(principal.username == #reservation.user.email)")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String reserveRoom(@Valid @ModelAttribute("reservation") Reservation reservation) {
		reservationService.save(reservation);
		return "reservationsuccess";
	}

	@RequestMapping("/rooms/{id}")
	public String roomDetail(@PathVariable("id") Room room,
	                         Model model,
	                         HttpServletRequest request,
	                         @AuthenticationPrincipal MyUserDetails userDetails) throws JsonProcessingException {
		model.addAttribute("reservation", new Reservation());
		Optional.ofNullable(userDetails)
			.map(MyUserDetails::getUser)
			.ifPresent(user -> model.addAttribute("user", user));
		model.addAttribute("room", room);
		model.addAttribute("eventsJson", objectMapper.writeValueAsString(getEvents(room, request)));
		return "detail";
	}
}
