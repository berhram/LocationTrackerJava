package com.velvet.core.models.location.emitter;

import android.location.Location;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LocationEmitterDatabase extends LocationEmitterDecorator {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final PublishSubject<Result<List<Location>>> locationSubject = PublishSubject.create();

    @Inject
    FirebaseFirestore database;

    public LocationEmitterDatabase(LocationEmitter locationEmitter) {
        super(locationEmitter);
        database.setFirestoreSettings(
                new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(Values.MAX_CACHE_BYTES)
                .build());
    }

    @Override
    public void start() {
        super.start();
        disposables.add(
                locationSubject
                        .map(listResult -> listResult.data)
                        .flatMap(Observable::fromIterable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::writeLocationToFirestore)
        );
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void writeLocationToFirestore(Location location) {
        database.collection("Tracker")
                .add(location);
    }

    @Override
    public Single<Result<List<Location>>> getLocations() {

        return super.getLocations();
    }
}
