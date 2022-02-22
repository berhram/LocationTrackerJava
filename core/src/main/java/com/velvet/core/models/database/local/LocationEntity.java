package com.velvet.core.models.database.local;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LocationEntity {
    @PrimaryKey
    public int id;

    //TODO deal with this error
    @ColumnInfo(name = "location")
    public Location location;
}
