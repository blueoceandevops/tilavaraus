package fi.xamk.tilavaraus.domain.validation;

import fi.xamk.tilavaraus.domain.Reservation;
import fi.xamk.tilavaraus.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;

@Component
public class ReservationValidator implements Validator {

	private final ReservationRepository reservationRepository;

	private static final List<TemporalAccessor> PUBLIC_HOLIDAYS = Arrays.asList(
		MonthDay.of(Month.JANUARY, 1),
		MonthDay.of(Month.JANUARY, 6),
		MonthDay.of(Month.MAY, 1),
		MonthDay.of(Month.DECEMBER, 6),
		MonthDay.of(Month.DECEMBER, 24),
		MonthDay.of(Month.DECEMBER, 25),
		MonthDay.of(Month.DECEMBER, 26),
		DayOfWeek.SUNDAY
	);

	private boolean isOnHoliday(LocalDate localDate) {
		return PUBLIC_HOLIDAYS.stream().anyMatch(
			temporalAccessor -> MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth()).equals(temporalAccessor) || localDate.getDayOfWeek().equals(temporalAccessor)
		);
	}


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

		if (isOnHoliday(reservation.getDate())) {
			errors.reject("validation.reservationOnHoliday", "Cannot make reservations on holidays!");
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
