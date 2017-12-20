package com.actitime.utils;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindingHelper {
    public static BindingResult bindMapToDomain(Object domain, Map properties, Validator... validators) {
        DataBinder binder = new DataBinder(domain);
        binder.addValidators(validators);
        PropertyValues propertyValues = new MutablePropertyValues(properties);
        binder.bind(propertyValues);
        binder.validate();
        return binder.getBindingResult();
    }

    public static class CustomErrors {
        private final Map errors;

        public Map getErrors() {
            return errors;
        }

        public CustomErrors(List<FieldError> fieldErrors) {
            Map<String, String> map = new HashMap<>();
            for (FieldError fieldError : fieldErrors)
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            errors = Collections.unmodifiableMap(map);
        }

        public CustomErrors(String key, String value) {
            errors = Collections.unmodifiableMap(Collections.singletonMap(key, value));
        }
    }
}
