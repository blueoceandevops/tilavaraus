package fi.xamk.tilavaraus.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeWindowValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeWindow {
	String message() default "Time is not allowed";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int from();

	int to();
}
