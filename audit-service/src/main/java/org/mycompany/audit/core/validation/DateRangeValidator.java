package org.mycompany.audit.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {
    private String fromProperty;
    private String toProperty;

    public void initialize(DateRange constraintAnnotation) {
        this.fromProperty = constraintAnnotation.from();
        this.toProperty = constraintAnnotation.to();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        LocalDate from = (LocalDate) beanWrapper.getPropertyValue(fromProperty);
        LocalDate to = (LocalDate) beanWrapper.getPropertyValue(toProperty);

        return from == null || to == null || from.isBefore(to) || from.isEqual(to);
    }
}
