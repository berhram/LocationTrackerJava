package com.velvet.trackerforsleepwalkers.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import com.velvet.trackerforsleepwalkers.R;

import java.text.DateFormat;
import java.util.Date;

public class LocationUtils {
    private static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";
    public static final String ACTION_STOP_SERVICE = "stop_location_update";

    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }


    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }


}
