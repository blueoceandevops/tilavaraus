package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
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

	@PostMapping("/rooms/{id}/reserve")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String reserveRoom(@PathVariable("id") Room room,
	                          @RequestParam("count") Integer count,
	                          @RequestParam("startTime") String startTime,
	                          @RequestParam("endTime") String endTime,
	                          @RequestParam("additionalServices") List<String> additionalServices,
	                          @AuthenticationPrincipal MyUserDetails myUserDetails) {
		Instant start = Instant.parse(startTime + ":00.00Z");
		Instant end = Instant.parse(endTime + ":00.00Z");

		if (!reservationRepository.findOverlapping(start, end, room).isEmpty()) {
			throw new RuntimeException("Cannot make overlapping reservations!");
		}

		Reservation reservation = new Reservation();
		reservation.setPersonCount(count);
		reservation.setStartTime(start);
		reservation.setEndTime(end);
		reservation.setUser(myUserDetails.getUser());
		reservation.setAdditionalServices(additionalServices);
		reservation.setRoom(room);
		reservationRepository.save(reservation);
		return "redirect:/rooms/" + room.getId();
	}

	@RequestMapping("/rooms/{id}")
	public String roomDetail(Model model, @PathVariable("id") Room room) {
		model.addAttribute("room", room);
		model.addAttribute("additionalServices", Collections.singletonList("additionalServices.coffee"));
		return "detail";
	}

	@RequestMapping("/")
	public String foo(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "index";
	}
}
