package com.velvet.core.models.database.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.models.database.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.List;

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
            Tasks.await(task);
            if (task.isSuccessful()) {
                return Completable.complete();
            } else {
                return Completable.error(task.getException());
            }
        });
    }

    @Override
    public Single<Result<List<SimpleLocation>>> downloadLocations() {
        return Single.fromCallable(() -> {
            Task<DataSnapshot> task = remoteDatabase.get();
            Tasks.await(task);
            if (task.isSuccessful()) {
                return Result.success((List<SimpleLocation>) task.getResult().getValue(List.class));
            } else {
                return Result.error(task.getException());
            }
        });
    }
}
