package com.velvet.core.models.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.velvet.core.models.database.SimpleLocation;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM SimpleLocation")
    List<SimpleLocation> getAll();

    @Insert
    void insert(SimpleLocation locations);

    @Insert
    void insertAll(List<SimpleLocation> locations);

    @Delete
    void delete(SimpleLocation location);

    @Delete
    void deleteAll(List<SimpleLocation> location);
}
