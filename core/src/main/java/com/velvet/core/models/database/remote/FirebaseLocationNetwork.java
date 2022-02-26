package com.velvet.core.models.database.remote;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.velvet.core.models.database.local.Converters;
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
    public Single<Result<SimpleLocation>> saveLocationToRemote(SimpleLocation location) {
        return Single.fromCallable(() -> {
            remoteDatabase.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List locations = (List) task.getResult().getValue();
                    locations.add(location);
                    return Result.success(location);
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                    remoteDatabase.setValue(new ArrayList<SimpleLocation>()).addOnCompleteListener(taskCreate -> {
                        if (taskCreate.isSuccessful()) {
                            //TODO repeat task?
                        } else {
                            return Result.error(taskCreate.getException())
                        }
                    });
                }
            });


        });
    }

    @Override
    public Completable saveLocationToLocal(Result<SimpleLocation> locationResult) {
        return Completable.fromRunnable(() -> {
            dao.insert(locationResult.data);
        });
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocationsFromLocal() {
        return Single.fromCallable(() -> Result.success(dao.getAll()));
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocationsFromRemote() {
        return Single.fromCallable(() -> {
            Task<DataSnapshot> task = remoteDatabase.child("location").get();
            if (task.isSuccessful()) {
                return Result.success((List) task.getResult().getValue());
            } else {
                return Result.error(task.getException());
            }
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
