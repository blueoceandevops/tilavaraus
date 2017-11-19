package fi.xamk.tilavaraus.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.xamk.tilavaraus.domain.*;
import fi.xamk.tilavaraus.domain.validation.ReservationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FooController {

	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;
	private final ObjectMapper objectMapper;
	private final JavaMailSender mailSender;
	private final ReservationValidator reservationValidator;

	@Autowired
	public FooController(RoomRepository roomRepository, ReservationRepository reservationRepository, ObjectMapper objectMapper, JavaMailSender mailSender, ReservationValidator reservationValidator) {
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
		this.objectMapper = objectMapper;
		this.mailSender = mailSender;
		this.reservationValidator = reservationValidator;
	}

	@GetMapping("/reservations/{id}/delete")
	@Secured("ROLE_ADMIN")
	public String deleteReservations(@PathVariable("id") Reservation reservation) {
		reservationRepository.delete(reservation);
		return "redirect:/";
	}

	@GetMapping("/reservations/{id}/cancel")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String cancelReservation(@PathVariable("id") Reservation reservation) {
		if (reservation.getStartTime().isBefore(LocalDateTime.now().plus(7, ChronoUnit.DAYS))) {
			throw new RuntimeException("Cannot cancel reservation because it starts too soon!");
		}

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
	                          @AuthenticationPrincipal MyUserDetails myUserDetails,
	                          HttpServletRequest request) throws JsonProcessingException {
		reservation.setUser(myUserDetails.getUser());
		reservation.setRoom(room);
		reservationValidator.validate(reservation, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("reservation", reservation);
			model.addAttribute("room", room);
			model.addAttribute("eventsJson", objectMapper.writeValueAsString(getEvents(room, request)));
			return "detail";
		}


		reservationRepository.save(reservation);

		new Thread(() -> {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("Varausvahvistus");
			mailMessage.setTo(myUserDetails.getUser().getEmail());
			mailMessage.setText("Tila " + room.getName() + " varattu " + reservation.getPersonCount() + " henkilölle ajalle " +
					reservation.getStartTime().toString() + " - " + reservation.getEndTime().toString() + ".");
			mailSender.send(mailMessage);
		}).start();

		return "redirect:/rooms/" + room.getId();
	}

	@RequestMapping("/rooms/{id}")
	public String roomDetail(@PathVariable("id") Room room, Model model, HttpServletRequest request) throws JsonProcessingException {
		model.addAttribute("reservation", new Reservation());
		model.addAttribute("room", room);
		model.addAttribute("eventsJson", objectMapper.writeValueAsString(getEvents(room, request)));
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
