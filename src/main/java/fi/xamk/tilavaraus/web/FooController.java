package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FooController {

	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;

	@Autowired
	public FooController(RoomRepository roomRepository, ReservationRepository reservationRepository) {
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
	}

	@GetMapping("/reservations/{id}/delete")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String deleteReservations(@PathVariable("id") Reservation reservation) {
		reservationRepository.delete(reservation);
		return "redirect:/";
	}

	@GetMapping("/myreservations")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String myReservations(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		model.addAttribute("reservations", reservationRepository.findByUser(myUserDetails.getUser()));
		return "myreservations";
	}

	@GetMapping("/rooms/{id}/events")
	@ResponseBody
	public Iterable<FullCalendarEvent> getEvents(@PathVariable("id") Room room, HttpServletRequest httpServletRequest) {
		return reservationRepository.findByRoom(room).stream()
				.map(reservation -> new FullCalendarEvent(reservation.getId().toString(),
						httpServletRequest.isUserInRole("ADMIN") ? reservation.getUser().getEmail() : "",
						reservation.getStartTime(),
						reservation.getEndTime()))
				.collect(Collectors.toList());
	}

	@PostMapping("/rooms/{id}")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String reserveRoom(@Valid @ModelAttribute("reservation") Reservation reservation,
	                          BindingResult bindingResult,
	                          @PathVariable("id") Room room,
	                          Model model,
	                          @AuthenticationPrincipal MyUserDetails myUserDetails) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("room", room);
			return "detail";
		}

		if (!reservationRepository.findOverlapping(reservation.getStartTime(), reservation.getEndTime(), room).isEmpty()) {
			throw new RuntimeException("Cannot make overlapping reservations!");
		}
		reservation.setUser(myUserDetails.getUser());
		reservation.setRoom(room);
		reservationRepository.save(reservation);
		return "redirect:/rooms/" + room.getId();
	}

	@RequestMapping("/rooms/{id}")
	public String roomDetail(@PathVariable("id") Room room, Model model) {
		model.addAttribute("reservation", new Reservation());
		model.addAttribute("room", room);
		return "detail";
	}

	@ModelAttribute("additionalServices")
	public List<String> getAdditionalServices() {
		return Collections.singletonList("additionalServices.coffee");
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "index";
	}
}
