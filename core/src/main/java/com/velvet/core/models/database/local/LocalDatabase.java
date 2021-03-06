package com.velvet.core.models.database.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.velvet.core.models.database.SimpleLocation;

@Database(entities = {SimpleLocation.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
