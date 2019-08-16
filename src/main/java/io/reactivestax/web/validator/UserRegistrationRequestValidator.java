package io.reactivestax.web.validator;

import io.reactivestax.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Component
@Slf4j
public class UserRegistrationRequestValidator implements Validator {

    private static final String MM_DD_YYYY_DATE_FORMAT = "mm-dd-yyyy";

    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        log.debug("start UserRegistrationRequestValidator");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "field.required");

        User user = (User) object;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(MM_DD_YYYY_DATE_FORMAT);
        dateFormatter.setLenient(false);

        try {
            if(user.getDob()!=null) dateFormatter.parse(user.getDob());

        } catch (ParseException pe) {
            errors.rejectValue("dob", "date.format.invalid");
            log.error("Exception validating date {} using the date format {} ", object, MM_DD_YYYY_DATE_FORMAT);
        }
        log.debug("end UserRegistrationRequestValidator");
    }

}
