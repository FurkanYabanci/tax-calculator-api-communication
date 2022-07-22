package com.yabanci.ayrotektaxcalculator.exception;


import com.yabanci.ayrotektaxcalculator.enums.VatErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(VatErrorMessage message) {
        super(message.getMessage());
    }

}