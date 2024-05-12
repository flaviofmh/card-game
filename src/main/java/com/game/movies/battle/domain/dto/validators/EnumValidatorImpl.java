package com.game.movies.battle.domain.dto.validators;

import com.game.movies.battle.domain.dto.MovieType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        enumClass = constraintAnnotation.enumClazz();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }

        for (Enum<?> enumVal : enumClass.getEnumConstants()) {
            if (enumVal.name().equals(value)) {
                return true;
            }
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Invalid value for status: " + value + ". Allowed values are: " + Arrays.toString(enumClass.getEnumConstants())
        ).addConstraintViolation();

        return false;
    }
}

