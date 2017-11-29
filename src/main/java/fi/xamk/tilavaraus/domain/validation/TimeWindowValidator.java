package fi.xamk.tilavaraus.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class TimeWindowValidator implements ConstraintValidator<TimeWindow, LocalTime> {
	private TimeWindow constraint;

	public void initialize(TimeWindow constraint) {
		this.constraint = constraint;
	}

	public boolean isValid(LocalTime localTime, ConstraintValidatorContext context) {
		if (localTime == null) return true;

		LocalTime from = LocalTime.of(constraint.from(), 0);
		LocalTime to = LocalTime.of(constraint.to(), 0);

		return !(localTime.isBefore(from) || localTime.isAfter(to));
	}
}
