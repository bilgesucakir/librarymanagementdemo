package com.example.librarymanagementdemo.validation;

import com.example.librarymanagementdemo.validation.annotation.DateRange;

import org.apache.commons.beanutils.BeanUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Date;

public class DateRangeValidator implements ConstraintValidator<DateRange,Object> {


    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(DateRange constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        Date firstDate = null;
        Date secondDate = null;

        try {
            firstDate = getDateField(value, firstFieldName);
            secondDate = getDateField(value, secondFieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (firstDate == null || secondDate == null) {
            return true;
        }
        if(firstDate.after(secondDate)){
            System.out.println("invalid");
            return false;
        }
        else{
            System.out.println("valid");
            return true;
        }

    }

    private Date getDateField(Object value, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = value.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Date) field.get(value);
    }
}
