package com.velvet.trackerforsleepwalkers.models.location;

import android.content.Context;
import android.preference.PreferenceManager;

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
