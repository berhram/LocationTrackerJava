package com.velvet.core.models.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
        @Query("SELECT * FROM locationentity")
        List<LocationEntity> getAll();

        @Insert
        void insert(LocationEntity locations);

        @Delete
        void delete(LocationEntity location);
}
