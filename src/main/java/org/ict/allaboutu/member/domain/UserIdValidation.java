package org.ict.allaboutu.member.domain;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.*;
import jakarta.validation.*;

@Documented
@Constraint(validatedBy = UserIdValidation.UserIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdValidation {
    String message() default "아이디 유효성 검사";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // userId 유효성 검사 (admin 포함 여부)
    class UserIdValidator implements ConstraintValidator<UserIdValidation, String> {
        @Override
        public void initialize(UserIdValidation constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value == null || !value.contains("admin");
        }
    }
}


