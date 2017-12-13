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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
	public String cancelReservation(@PathVariable("id") Reservation reservation, RedirectAttributes redirectAttributes) {
		reservationService.cancel(reservation);
		redirectAttributes.addFlashAttribute("alert", "info:reservationCancelled");
		return "redirect:/myreservations";
	}

	private Function<Reservation, FullCalendarEvent> toFullCalendarEvent(boolean isAdmin) {
		return reservation -> FullCalendarEvent.builder()
			.id(reservation.getId().toString())
			.title(isAdmin ? reservation.getUser().getEmail() : "")
			.start(LocalDateTime.of(reservation.getDate(), reservation.getStartTime()))
			.end(LocalDateTime.of(reservation.getDate(), reservation.getEndTime()))
			.url(isAdmin ? "/admin/reservations/" + reservation.getId() + "/edit" : null)
			.build();
	}

	@GetMapping("/rooms/{id}/events")
	@ResponseBody
	public Iterable<FullCalendarEvent> getEvents(@PathVariable("id") Room room, HttpServletRequest httpServletRequest) {
		return reservationRepository.findByRoom(room).stream()
			.map(toFullCalendarEvent(httpServletRequest.isUserInRole("ADMIN")))
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
		Map<Boolean, List<Reservation>> reservationsByIsOld = reservationRepository.findByUser(myUserDetails.getUser())
			.stream()
			.collect(Collectors.groupingBy(Reservation::isOld));
		model.addAttribute("newReservations", reservationsByIsOld.get(false));
		model.addAttribute("oldReservations", reservationsByIsOld.get(true));
		return "myreservations";
	}

	@InitBinder("reservation")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(reservationValidator);
	}

	@PostMapping("/reserve")
	@PreAuthorize("(principal.username == #reservation.user.email)")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String reserveRoom(@Valid @ModelAttribute("reservation") Reservation reservation, Model model) {
		reservationService.save(reservation);
		model.addAttribute("alert", "success:reservationSuccess");
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
