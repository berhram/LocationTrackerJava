package com.velvet.core;

public class Values {
    public static final String REQUEST = "request";
    public static final String CHECK = "check";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";

    public static final long LOCATION_WRITE_FREQUENTLY_SEC  = 5;
    public static final long LOCATION_WRITE_FREQUENTLY_MILLIS  = LOCATION_WRITE_FREQUENTLY_SEC * 1000;
    public static final long LOCATION_READ_FREQUENTLY_SEC  = 20 * 60;

    public static final long MAX_CACHE_BYTES = 1024 * 1024 * 10;

    public static final String DATE_PATTERN = "yyyy.MM.dd HH:mm:ss";
}
