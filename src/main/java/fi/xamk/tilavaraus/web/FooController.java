package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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

	@GetMapping("/{id}/events")
	@ResponseBody
	public Iterable<FullCalendarEvent> getEvents(@PathVariable("id") Long id) {
		Room room = roomRepository.findById(id).get();
		return reservationRepository.findByRoom(room).stream()
				.map(reservation -> new FullCalendarEvent(reservation.getId().toString(),
						reservation.getId().toString(),
						reservation.getStartTime(),
						reservation.getEndTime()))
				.collect(Collectors.toList());
	}

	@PostMapping("/{id}/reserve")
	@ResponseBody
	public String reserveRoom(@PathVariable("id") Long id,
	                          @RequestParam("count") Integer count,
	                          @RequestParam("startTime") String startTime,
	                          @RequestParam("endTime") String endTime,
	                          @RequestParam("additionalServices") List<String> additionalServices) {
		Reservation reservation = new Reservation();
		reservation.setPersonCount(count);
		reservation.setStartTime(Instant.parse(startTime + ":00.00Z"));
		reservation.setEndTime(Instant.parse(endTime + ":00.00Z"));
		reservation.setUser("foo");
		reservation.setAdditionalServices(additionalServices);
		reservation.setRoom(roomRepository.findById(id).get());
		reservationRepository.save(reservation);
		return "Varattu tila " + id + " " + count + " henkilölle!";
	}

	@RequestMapping("/{id}")
	public String roomDetail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("room", roomRepository.findById(id).get());
		return "detail";
	}

	@RequestMapping("/")
	public String foo(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "foo";
	}
}
