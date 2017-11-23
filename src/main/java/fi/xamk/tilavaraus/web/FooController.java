package fi.xamk.tilavaraus.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.xamk.tilavaraus.domain.*;
import fi.xamk.tilavaraus.domain.validation.ReservationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class FooController {

	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;
	private final ObjectMapper objectMapper;
	private final Optional<JavaMailSender> mailSender;
	private final ReservationValidator reservationValidator;
	private final AdditionalServiceRepository additionalServiceRepository;

	@Autowired
	public FooController(RoomRepository roomRepository,
						 ReservationRepository reservationRepository,
						 ObjectMapper objectMapper,
						 Optional<JavaMailSender> mailSender,
						 ReservationValidator reservationValidator,
						 AdditionalServiceRepository additionalServiceRepository) {
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
		this.objectMapper = objectMapper;
		this.mailSender = mailSender;
		this.reservationValidator = reservationValidator;
		this.additionalServiceRepository = additionalServiceRepository;
	}

	@GetMapping("/reservations/{id}/cancel")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PreAuthorize("(principal.username == #reservation.user.email) && #reservation.cancellable")
	public String cancelReservation(@PathVariable("id") Reservation reservation) {
		reservationRepository.delete(reservation);
		return "redirect:/myreservations";
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
			model.addAttribute("room", room);
			model.addAttribute("eventsJson", objectMapper.writeValueAsString(getEvents(room, request)));
			return "detail";
		}

		reservationRepository.save(reservation);

		mailSender.ifPresent(javaMailSender -> new Thread(() -> {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("Varausvahvistus");
			mailMessage.setTo(myUserDetails.getUser().getEmail());
			mailMessage.setText("Tila " + room.getName() + " varattu " + reservation.getPersonCount() + " henkilölle ajalle " +
					reservation.getStartTime().toString() + " - " + reservation.getEndTime().toString() + ".");
			javaMailSender.send(mailMessage);
		}).start());

		return "reservationsuccess";
	}

	@RequestMapping("/rooms/{id}")
	public String roomDetail(@PathVariable("id") Room room, Model model, HttpServletRequest request) throws JsonProcessingException {
		model.addAttribute("reservation", new Reservation());
		model.addAttribute("room", room);
		model.addAttribute("eventsJson", objectMapper.writeValueAsString(getEvents(room, request)));
		return "detail";
	}

	@ModelAttribute("additionalServices")
	public Iterable<AdditionalService> getAdditionalServices() {
		return additionalServiceRepository.findAll();
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "index";
	}
}
