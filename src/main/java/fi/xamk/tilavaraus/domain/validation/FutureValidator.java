package fi.xamk.tilavaraus.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDateTime;

public class FutureValidator implements ConstraintValidator<Future, LocalDateTime> {

    private Future constraint;

    public void initialize(Future constraint) {
        this.constraint = constraint;
    }

	public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
		return localDateTime == null || localDateTime.minus(Duration.ofDays(constraint.days()))
                .minus(Duration.ofHours(constraint.hours()))
                .minus(Duration.ofMinutes(constraint.minutes()))
                .minus(Duration.ofSeconds(constraint.seconds()))
				.isAfter(LocalDateTime.now());

    }
}
