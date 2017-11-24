package fi.xamk.tilavaraus.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class TimeWindowValidator implements ConstraintValidator<TimeWindow, LocalDateTime> {
	private TimeWindow constraint;

	public void initialize(TimeWindow constraint) {
		this.constraint = constraint;
	}

	public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
		if (localDateTime == null) return true;
		int hour = localDateTime.getHour();
		return hour >= constraint.from() && hour <= constraint.to();
	}
}
