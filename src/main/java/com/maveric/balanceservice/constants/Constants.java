package com.maveric.balanceservice.constants;

import java.time.LocalDateTime;

public class Constants {
    private Constants()
    {

    }
    public static final String BALANCE_NOT_FOUND_CODE="404";

    public static final String BAD_REQUEST_CODE="400";
    public static final String BAD_REQUEST_MESSAGE="Invalid inputs!";
    public static LocalDateTime getCurrentDateTime() {
        return (LocalDateTime.now());
    }
}
