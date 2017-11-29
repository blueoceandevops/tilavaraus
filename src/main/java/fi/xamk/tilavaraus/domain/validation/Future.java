package fi.xamk.tilavaraus.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Future {
	long days() default 0;

	Class<?>[] groups() default {};

	long hours() default 0;

	String message() default "Field is not enough in future";

	Class<? extends Payload>[] payload() default {};

}
