package com.iche.xpresspayapi.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.xpresspayapi.exceptions.InputNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class Validations<T> {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validation = validatorFactory.getValidator();

    public void validate(T request) {
        ObjectMapper objectMapper = new ObjectMapper();
        Set<ConstraintViolation<T>> validations = validation.validate(request);
        Set<String> errorMessagesSet = new HashSet<>();

        if (!validations.isEmpty()) {
            for (ConstraintViolation<T> violation : validations) {
                errorMessagesSet.add(violation.getMessage());
            }

            try {
                String jsonErrorMessage = objectMapper.writeValueAsString(errorMessagesSet);
                throw new InputNotValidException(jsonErrorMessage);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


}
