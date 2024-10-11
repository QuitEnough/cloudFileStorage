package ru.cloudfilestorage.cloudfilestorage.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidUserId.UserIdValidator.class })
public @interface ValidUserId {

    String message() default "Must be just numbers";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    class UserIdValidator implements ConstraintValidator<ValidUserId, String> {
        private static final Pattern VALID_PATTERN = Pattern.compile("^[0-9]+$");

        @Override
        public boolean isValid(String userId, ConstraintValidatorContext context) {

            if (userId == null) {
                return false;
            }

            return true;//VALID_PATTERN.matcher(userId).matches();
        }
    }
}

