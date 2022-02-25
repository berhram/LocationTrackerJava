package com.velvet.core.models.database.local;

import android.location.Location;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public Date longToDate(long date) {
        return new Date(date);
    }

    @TypeConverter
    public long longToDate(Date date) {
        return date.getTime();
    }

    static public LocationEntity locationToEntity(Location location) {
        return new LocationEntity(new Date(location.getTime()), location.getLatitude(), location.getLongitude());
    }

    static public Location entityToLocation(LocationEntity location) {
        Location output = new Location("");
        output.setLatitude(location.latitude);
        output.setLongitude(location.longitude);
        output.setTime(location.date.getTime());
        return output;
    }
}
