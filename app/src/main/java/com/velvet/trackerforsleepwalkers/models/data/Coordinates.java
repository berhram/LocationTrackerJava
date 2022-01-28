package com.velvet.trackerforsleepwalkers.models.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.LinkedList;

@Entity
public class Coordinates {
    @PrimaryKey
    public Date date;

    @ColumnInfo(name = "coordinates")
    public LatLng coordinates;
}
