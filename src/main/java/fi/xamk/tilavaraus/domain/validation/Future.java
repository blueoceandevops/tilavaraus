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
    String message() default "Field is not enough in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long days() default 0;

    long hours() default 0;

    long minutes() default 0;

    long seconds() default 0;

}
