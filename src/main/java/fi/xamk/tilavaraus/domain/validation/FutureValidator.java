package fi.xamk.tilavaraus.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.Instant;

public class FutureValidator implements ConstraintValidator<Future, Instant> {

    private Future constraint;


    public void initialize(Future constraint) {
        this.constraint = constraint;
    }

    public boolean isValid(Instant instant, ConstraintValidatorContext context) {
        return instant
                .minus(Duration.ofDays(constraint.days()))
                .minus(Duration.ofHours(constraint.hours()))
                .minus(Duration.ofMinutes(constraint.minutes()))
                .minus(Duration.ofSeconds(constraint.seconds()))
                .isAfter(Instant.now());
    }
}
