package com.greenblat.socialmedia.validation;

import com.greenblat.socialmedia.validation.impl.UniqueUserInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserInfo {

    String message() default "Username and Email should be unique";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
