package com.velvet.core;

public class Values {
    public static final String REQUEST = "request";
    public static final String CHECK = "check";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";

    public static final long LOCATION_CHECK_FREQUENTLY_SEC  = 5;
    public static final long LOCATION_CHECK_FREQUENTLY_MILLIS  = LOCATION_CHECK_FREQUENTLY_SEC * 1000;
    //prod
    //public static final long LOCATION_CHECK_FREQUENTLY_SEC  = 20 * 60;

    public static final long MAX_CACHE_BYTES = 1024 * 1024 * 10;

    public static final String DATE_PATTERN = "yyyy.MM.dd HH:mm:ss";

    public static final String START = "start";
    public static final String STOP = "stop";

    public static final String CHANNEL_ID = "Tracker channel";
}
