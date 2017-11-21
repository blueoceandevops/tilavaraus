package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.Room;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RequestMapping("/api")
@RestController
public class ApiController {

	@PostMapping("/calculatePrice")
	public BigDecimal calculatePrice(@Valid @ModelAttribute("reservation") Reservation reservation, @RequestParam("roomId") Room room) {
		reservation.setRoom(room);
		return reservation.getTotalPrice();
	}

}
