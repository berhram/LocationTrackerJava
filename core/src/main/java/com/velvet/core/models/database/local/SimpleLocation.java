package com.velvet.core.models.database.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SimpleLocation {
    public SimpleLocation(long time, double latitude, double longitude) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @PrimaryKey
    public long time;

    public double latitude;

    public double longitude;


}
