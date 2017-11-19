package fi.xamk.tilavaraus.domain.validation;

import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Duration;

@Component
public class ReservationValidator implements Validator {

	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationValidator(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Reservation.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Reservation reservation = (Reservation) target;
		if (!reservationRepository.findOverlapping(reservation.getStartTime(), reservation.getEndTime(), reservation.getRoom()).isEmpty()) {
			errors.reject("validation.overLappingReservation", "Cannot make overlapping reservations!");
		}

		if (reservation.getDuration().minus(Duration.ofHours(1)).isNegative()) {
			errors.reject("validation.tooShortReservation", "Too short reservation!");
		}
	}
}
