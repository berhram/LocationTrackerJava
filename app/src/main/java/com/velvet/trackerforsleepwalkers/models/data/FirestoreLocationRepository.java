package com.velvet.trackerforsleepwalkers.models.data;

import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.trackerforsleepwalkers.App;

import javax.inject.Inject;

public class FirestoreLocationRepository implements LocationRepository {
    @Inject
    FirebaseFirestore firestore;

    public FirestoreLocationRepository() {
        App.getInstance().getComponent().inject(this);
    }

}
