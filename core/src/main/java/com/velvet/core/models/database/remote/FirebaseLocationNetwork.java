package com.velvet.core.models.database.remote;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.velvet.core.Converters;
import com.velvet.core.models.database.local.LocalDatabase;
import com.velvet.core.models.database.local.LocationDao;
import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseLocationNetwork implements LocationNetwork {
    private final DatabaseReference remoteDatabase = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
    private final LocalDatabase localDatabase;
    private final LocationDao dao;

    public FirebaseLocationNetwork(Context ctx) {
        localDatabase = Room.databaseBuilder(ctx, LocalDatabase.class, "Local storage").build();
        dao = localDatabase.locationDao();
    }

    @Override
    public Completable saveLocationToRemote(List<SimpleLocation> locationList) {
        return Completable.fromCallable(() -> {
            for (SimpleLocation location : locationList) {
                Task<Void> task = remoteDatabase.push().setValue(location);
                Tasks.await(task);
                if (!task.isSuccessful()) {
                    return Completable.error(task.getException());
                }
            }
            return Completable.complete();
        });
    }

    @Override
    public Completable saveSingleLocationToRemote(SimpleLocation location) {
        return Completable.fromCallable(() -> {
            Task<Void> task = remoteDatabase.push().setValue(location);
            Tasks.await(task);
            if (task.isSuccessful()) {
                return Completable.complete();
            } else {
                return Completable.error(task.getException());
            }
        });
    }

    @Override
    public Completable saveLocationToLocal(List<SimpleLocation> locationList) {
        return Completable.fromRunnable(() -> {
            for (SimpleLocation location:
                 locationList) {
                dao.insert(location);
            }
        });
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocationsFromLocal() {
        return Single.fromCallable(() -> Result.success(dao.getAll()));
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocationsFromRemote() {
        return Single.fromCallable(() -> {
            Task<DataSnapshot> task = remoteDatabase.get();
            Tasks.await(task);
            List<SimpleLocation> output = new ArrayList<>();
            if (task.isSuccessful()) {
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    output.add(dataSnapshot.getValue(SimpleLocation.class));
                }
            } else {
                return Result.error(task.getException());
            }
            return Result.success(output);
        });
    }

    @Override
    public Completable deleteLocationFromLocal(SimpleLocation location) {
        return Completable.fromCallable(() -> {
            dao.delete(location);
            return Completable.complete();
        });
    }
}
