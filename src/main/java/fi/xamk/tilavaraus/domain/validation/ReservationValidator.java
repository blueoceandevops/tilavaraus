package fi.xamk.tilavaraus.domain.validation;

import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Duration;
import java.time.Month;

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

		if (reservation.getPersonCount() > reservation.getRoom().getCapacity()) {
			errors.reject("validation.tooMuchPersons", "Too much persons!");
		}

		if (reservation.getDate().getMonth().equals(Month.JULY)) {
			errors.reject("validation.reservationInJuly", "Cannot make reservations in July!");
		}

		if (reservation.getDuration().minus(Duration.ofHours(1)).isNegative()) {
			errors.reject("validation.tooShortReservation", "Too short reservation!");
		}

		if (!reservationRepository.findOverlapping(
			reservation.getStartTime(),
			reservation.getEndTime(),
			reservation.getDate(),
			reservation.getRoom()).isEmpty()
			) {
			errors.reject("validation.overLappingReservation", "Cannot make overlapping reservations!");
		}

		if (reservation.getAdditionalServices() != null && !reservation.getAdditionalServices()
			.stream()
			.allMatch(as -> reservation.getRoom().getAllowedAdditionalServices().contains(as))) {
			errors.rejectValue("additionalServices",
				"validation.unallowedAdditionalServices",
				"This room does not allow specified additional services!");
		}


	}
}
