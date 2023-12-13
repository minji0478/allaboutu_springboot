package org.ict.allaboutu.member.domain;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.*;
import jakarta.validation.*;

@Documented
@Constraint(validatedBy = UserPwdValidation.UserPwdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPwdValidation {
    String message() default "비밀번호 유효성 검사";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // userPwd 유효성 검사 (특수문자 포함 여부)
    class UserPwdValidator implements ConstraintValidator<UserPwdValidation, String> {
        @Override
        public void initialize(UserPwdValidation constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && value.matches(".*[~!@#$%^&*()_+|<>?:{}].*");
        }
    }
}
