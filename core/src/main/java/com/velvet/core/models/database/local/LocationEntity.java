package com.velvet.core.models.database.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
@TypeConverters(Converters.class)
public class LocationEntity {
    public LocationEntity(Date date, double latitude, double longitude) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @PrimaryKey
    public Date date;

    public double latitude;

    public double longitude;
}
