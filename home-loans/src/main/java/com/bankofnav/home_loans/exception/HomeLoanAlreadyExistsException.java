package com.bankofnav.home_loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HomeLoanAlreadyExistsException extends RuntimeException {

    public HomeLoanAlreadyExistsException(String message){
        super(message);
    }

}
