package com.velvet.core.models.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SimpleLocation {

    @PrimaryKey
    public final long time;
    public final double latitude;
    public final double longitude;

    public SimpleLocation(long time, double latitude, double longitude) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
