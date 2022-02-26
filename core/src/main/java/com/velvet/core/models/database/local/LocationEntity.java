package com.velvet.core.models.database.local;

import static com.velvet.core.models.database.local.Converters.timeToString;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class LocationEntity {
    public LocationEntity(String date, String latitude, String longitude) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @PrimaryKey
    public String date;

    public String latitude;

    public String longitude;

    static public LocationEntity locationToEntity(Location location) {
        return new LocationEntity(timeToString(location.getTime()),
                String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()));
    }
}
