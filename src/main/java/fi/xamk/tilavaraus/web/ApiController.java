package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.validation.ReservationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RequestMapping("/api")
@RestController
public class ApiController {
	private final ReservationValidator reservationValidator;

	@Autowired
	public ApiController(ReservationValidator reservationValidator) {
		this.reservationValidator = reservationValidator;
	}

	@PostMapping("/calculatePrice")
	public BigDecimal calculatePrice(@Valid @ModelAttribute("reservation") Reservation reservation) {
		return reservation.getTotalPrice();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(reservationValidator);
	}

}
