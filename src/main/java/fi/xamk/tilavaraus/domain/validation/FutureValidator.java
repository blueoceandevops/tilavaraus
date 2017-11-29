package fi.xamk.tilavaraus.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FutureValidator implements ConstraintValidator<Future, LocalDate> {

	private Future constraint;

	public void initialize(Future constraint) {
		this.constraint = constraint;
	}

	public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
		if (localDate == null) return true;
		return localDate.isAfter(LocalDate.now().plus(constraint.days(), ChronoUnit.DAYS));
	}
}
