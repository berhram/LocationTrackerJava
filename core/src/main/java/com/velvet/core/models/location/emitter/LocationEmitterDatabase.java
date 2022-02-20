package com.velvet.core.models.location.emitter;

import android.location.Location;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.result.Result;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LocationEmitterDatabase extends LocationEmitterDecorator {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final PublishSubject<Result<Location>> locationSubject = PublishSubject.create();

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

    public LocationEmitterDatabase(LocationEmitter locationEmitter) {
        super(locationEmitter);
        Log.d("LOC", "LocationEmitterDatabase created");
    }

    @Override
    public void start() {
        super.start();
        Log.d("LOC", "LocationEmitterDatabase started");
        disposables.add(
                locationSubject
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
        database.child("latitude").push().setValue(location.data.getLatitude());
        database.child("longitude").push().setValue(location.data.getLongitude());
        Log.d("LOC", "LocationEmitterDatabase writeLocationToFirestore invoked");
    }

    @Override
    public Result<Location> getLocation() {
        Log.d("LOC", "LocationEmitterDatabase getLocations invoked");
        locationSubject.onNext(super.getLocation());
        return super.getLocation();
    }
}
