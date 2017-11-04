package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

	private final ReservationRepository reservationRepository;

	@Autowired
	public AdminController(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@GetMapping("/reservations")
	public String listOrders(Model model) {
		model.addAttribute("reservations", reservationRepository.findAll());
		return "admin/reservations";
	}
}
