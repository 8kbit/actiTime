package com.actitime.validators;

import com.actitime.domain.User;
import com.actitime.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;

@Component(value = "userValidator")
public class UserValidator implements Validator {
    private final static String UNIQUE_CONSTRAINT_NAME = "com.actitime.validator.constraints.Unique.message";

    @Inject
    private UserRepository userRepository;

    @Inject
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target_, Errors errors) {
        User target = (User) target_;
        for (User user : userRepository.findAllByUserName(target.getUserName())) {
            if (!user.getId().equals(target.getId()))
                errors.rejectValue("userName", UNIQUE_CONSTRAINT_NAME, null,
                        messageSource.getMessage(UNIQUE_CONSTRAINT_NAME,
                                null, LocaleContextHolder.getLocale()));
        }
    }
}