package com.velvet.core.models.database.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.models.database.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseLocationNetwork implements LocationNetwork {
    private final DatabaseReference remoteDatabase = FirebaseDatabase.getInstance()
            .getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    public Completable uploadLocations(List<SimpleLocation> locationList) {
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
    public Completable uploadLocation(SimpleLocation location) {
        return Completable.fromCallable(() -> {
            Task<Void> task = remoteDatabase.push().setValue(location);
            try {
                Tasks.await(task, 3, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                throw e;
            }
            if (task.isSuccessful()) {
                return Completable.complete();
            } else {
                throw task.getException();
            }
        });
    }

    @Override
    public Single<Result<List<SimpleLocation>>> downloadLocations() {
        return Single.fromCallable(() -> {
            Task<DataSnapshot> task = remoteDatabase.get();
            Tasks.await(task);
            if (task.isSuccessful()) {
                List<SimpleLocation> output = new ArrayList<>();
                for (DataSnapshot d : task.getResult().getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) d.getValue();
                    SimpleLocation location = new SimpleLocation((Long) map.get("time"), (Double) map.get("latitude"), (Double) map.get("longitude"));
                    output.add(location);
                }
                return Result.success(output);
            } else {
                return Result.error(task.getException());
            }
        });
    }

    private Completable completeTask(Task task) throws Exception {
        try {
            Tasks.await(task, 3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw e;
        }
        if (task.isSuccessful()) {
            return Completable.complete();
        } else {
            throw task.getException();
        }
    }
}
