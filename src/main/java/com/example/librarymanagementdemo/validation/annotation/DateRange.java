package com.example.librarymanagementdemo.validation.annotation;

import java.lang.annotation.*;
import java.util.Date;

import com.example.librarymanagementdemo.validation.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { DateRangeValidator.class })
@Documented
public @interface DateRange {

    String message() default "Invalid data range given";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String first();
    String second();
}