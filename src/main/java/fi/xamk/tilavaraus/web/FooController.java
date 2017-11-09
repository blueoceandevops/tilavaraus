package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
	public String myReservations(Model model, Principal principal) {
		model.addAttribute("reservations", reservationRepository.findByUser(principal.getName()));
		return "myreservations";
	}

	@GetMapping("/rooms/{id}/events")
	@ResponseBody
	public Iterable<FullCalendarEvent> getEvents(@PathVariable("id") Room room, HttpServletRequest httpServletRequest) {
		return reservationRepository.findByRoom(room).stream()
				.map(reservation -> new FullCalendarEvent(reservation.getId().toString(),
						httpServletRequest.isUserInRole("ADMIN") ? reservation.getUser() : "",
						reservation.getStartTime(),
						reservation.getEndTime()))
				.collect(Collectors.toList());
	}

	@PostMapping("/rooms/{id}/reserve")
	@ResponseBody
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String reserveRoom(@PathVariable("id") Room room,
							  @RequestParam("count") Integer count,
	                          @RequestParam("startTime") String startTime,
	                          @RequestParam("endTime") String endTime,
	                          @RequestParam("additionalServices") List<String> additionalServices,
	                          Principal principal) {
		Reservation reservation = new Reservation();
		reservation.setPersonCount(count);
		reservation.setStartTime(Instant.parse(startTime + ":00.00Z"));
		reservation.setEndTime(Instant.parse(endTime + ":00.00Z"));
		Optional.ofNullable(principal).map(Principal::getName).ifPresent(reservation::setUser);
		reservation.setAdditionalServices(additionalServices);
		reservation.setRoom(room);
		reservationRepository.save(reservation);
		return "Varattu tila " + room.getId() + " " + count + " henkilölle!";
	}

	@RequestMapping("/rooms/{id}")
	public String roomDetail(Model model, @PathVariable("id") Room room) {
		model.addAttribute("room", room);
		return "detail";
	}

	@RequestMapping("/")
	public String foo(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "index";
	}
}
