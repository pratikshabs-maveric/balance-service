package com.maveric.balanceservice.exceptionhandler;

public class EnumException extends Exception {

    enum Currency {
        INR, USD;
    }


    public EnumException(String message) {
        super(message);
    }
}
