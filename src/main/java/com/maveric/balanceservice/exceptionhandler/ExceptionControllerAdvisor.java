package com.maveric.balanceservice.exceptionhandler;

import com.maveric.balanceservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.maveric.balanceservice.constants.Constants.*;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(BAD_REQUEST_CODE);
        System.out.println(ex.getMessage());

        if(ex.getMessage().contains("com.maveric.balanceservice.enumeration.Currency"))
            errorDto.setMessage(INVALID_INPUT_TYPE);
        else
            errorDto.setMessage(HttpMessageNotReadableException_MESSAGE);
        return errorDto;
    }
}
