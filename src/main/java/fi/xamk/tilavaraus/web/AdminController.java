package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.AdditionalServiceRepository;
import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import fi.xamk.tilavaraus.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

	private final ReservationRepository reservationRepository;
	private final AdditionalServiceRepository additionalServiceRepository;

	@Autowired
	public AdminController(ReservationRepository reservationRepository, AdditionalServiceRepository additionalServiceRepository) {
		this.reservationRepository = reservationRepository;
		this.additionalServiceRepository = additionalServiceRepository;
	}

	@GetMapping("/users/{id}")
	public String showUser(@PathVariable("id") User user, Model model) {
		model.addAttribute("user", user);
		return "user/profile";
	}

	@GetMapping("/reservations/{id}/delete")
	public String deleteReservations(@PathVariable("id") Reservation reservation) {
		reservationRepository.delete(reservation);
		return "redirect:/admin/reservations";
	}

	@PostMapping("/reservations/{id}/edit")
	public String editReservation(@PathVariable("id") Reservation existingReservation,
	                              @ModelAttribute("reservation") Reservation reservation) {
		existingReservation.setDate(reservation.getDate());
		existingReservation.setStartTime(reservation.getStartTime());
		existingReservation.setEndTime(reservation.getEndTime());
		existingReservation.setPersonCount(reservation.getPersonCount());
		existingReservation.setAdditionalServices(reservation.getAdditionalServices());
		reservationRepository.save(existingReservation);
		return "redirect:/admin/reservations";
	}

	@GetMapping("/reservations")
	public String listReservations(Model model) {
		model.addAttribute("reservations", reservationRepository.findAll());
		return "admin/reservations";
	}

	@GetMapping("/reservations/{id}/edit")
	public String showReservationEditForm(@PathVariable("id") Reservation reservation, Model model) {
		model.addAttribute("reservation", reservation);
		model.addAttribute("additionalServices", additionalServiceRepository.findAll());
		return "admin/editreservation";
	}
}
