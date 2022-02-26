package com.velvet.core.models.database.local;

import android.location.Location;

import androidx.room.TypeConverter;

import com.velvet.core.Values;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {
    private static final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    static public String dateToString(Date date) {
        return sDF.format(date);
    }

    static public String timeToString(long time) {
        return sDF.format(new Date(time));
    }

    static public Location stringToLocation(String latitude, String longitude) {
        Location output = new Location("");
        output.setLatitude(Double.parseDouble(latitude));
        output.setLongitude(Double.parseDouble(longitude));
        return output;
    }


}
