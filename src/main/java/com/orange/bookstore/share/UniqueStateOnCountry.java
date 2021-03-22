package com.orange.bookstore.share;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {UniqueStateOnCountryValidator.class})
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface UniqueStateOnCountry {

    String message() default "Não é permitido estado duplicado no mesmo país!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
