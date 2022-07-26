package com.yabanci.ayrotekproductuser.exception;


import com.yabanci.ayrotekproductuser.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(BaseErrorMessage message) {
        super(message.getMessage());
    }

}