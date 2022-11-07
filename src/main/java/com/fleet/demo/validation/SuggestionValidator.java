package com.fleet.demo.validation;

import com.fleet.demo.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SuggestionValidator implements Validator<Optional<String>> {

    @Override
    public boolean validate(Optional<String> input) {
        if (input.isPresent()) {
            Pattern pattern = Pattern.compile("^[a-zA-Z .,]*$");
            Matcher matcher = pattern.matcher(input.get());
            if (!matcher.matches()) {
                throw new ValidationException(
                        String.format("Invalid Input Query: %s, only allowed coma and Charter.", input.get()));
            }
        }
        return true;
    }
}
