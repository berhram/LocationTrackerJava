package com.velvet.core.models.location.emitter;

import android.location.Location;
import android.util.Log;

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
    private final PublishSubject<List<Result<Location>>> locationSubject = PublishSubject.create();

    FirebaseFirestore database = FirebaseFirestore.getInstance();

    public LocationEmitterDatabase(LocationEmitter locationEmitter) {
        super(locationEmitter);
        database.setFirestoreSettings(
                new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(Values.MAX_CACHE_BYTES)
                .build());
        Log.d("LOC", "LocationEmitterDatabase created");
    }

    @Override
    public void start() {
        super.start();
        Log.d("LOC", "LocationEmitterDatabase started");
        disposables.add(
                locationSubject
                        .flatMap(Observable::fromIterable)
                        .filter(locationResult -> !locationResult.isError())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::writeLocationToFirestore)
        );
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void writeLocationToFirestore(Result<Location> location) {
        database.collection("Tracker").add(location.data);
        Log.d("LOC", "LocationEmitterDatabase writeLocationToFirestore invoked");
    }

    @Override
    public Single<List<Result<Location>>> getLocations() {
        Log.d("LOC", "LocationEmitterDatabase getLocations invoked");
        super.getLocations().toObservable().subscribe(locationSubject);
        return super.getLocations();
    }
}
