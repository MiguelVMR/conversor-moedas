package com.wefin.conversor_moedas.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The @Interface PasswordConstraint
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/03/2025
 */
@Documented
@Constraint(validatedBy = PasswordConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default """
            Invalid password! Password must contain at least one digit [0-9],
            at least one lowercase Latin character [a-z], at least one uppercase
            Latin character [A-Z], at least one special character like !@#&()–:;',?/*~$^+=<>,
            a length of at least 6 characters and a maximum of 20 characters""";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
