package nsu.sber.validation;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotBlank
@Size(min = 4, max = 50, message = "Login must be 4-50 characters long")
@Pattern(
        regexp = "^[A-Za-z0-9_]+$",
        message = "Login must contain only letters, digits and underscores"
)
public @interface ValidLogin {
    String message() default "Login must be 4-50 characters long and contain only letters, digits and underscore";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
