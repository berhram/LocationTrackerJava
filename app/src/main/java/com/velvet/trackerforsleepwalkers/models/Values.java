package com.velvet.trackerforsleepwalkers.models;

import com.google.android.gms.location.LocationRequest;

public class Values {
    //set the desired interval for active location updates, in milliseconds
    public static int LOCATION_UPDATES = 10_000;

    //explicitly set the fastest interval for location updates, in milliseconds.
    public static int FASTEST_LOCATION_UPDATES = 5_000;

    //set accuracy
    public static int ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
}
