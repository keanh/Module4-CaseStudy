package com.codegym.casestudy.validate;

import com.codegym.casestudy.model.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PhoneValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        Account phoneNumber = (Account) target;
        String number = phoneNumber.getPhoneNumber();
        ValidationUtils.rejectIfEmpty(errors,"phoneNumber","number.empty");
        if (number.length() > 11 || number.length()<10){
            errors.rejectValue("phoneNumber","number.length");
        }
        if (!number.startsWith("0")){
            errors.rejectValue("phoneNumber","number.startsWith");
        }
        if (!number.matches("(^$|[0-9]*$)")){
            errors.rejectValue("phoneNumber","number.matches");
        }
    }
}
