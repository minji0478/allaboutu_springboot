package org.ict.allaboutu.member.domain;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.*;
import jakarta.validation.*;

@Documented
@Constraint(validatedBy = UserNameValidation.UserNameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNameValidation {
    String message() default "사용자 이름 유효성 검사";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // userName 유효성 검사 (4글자 이상이면 에러)
    class UserNameValidator implements ConstraintValidator<UserNameValidation, String> {
        @Override
        public void initialize(UserNameValidation constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && value.length() <= 6 && !value.contains("관리자");

        }
    }
}
