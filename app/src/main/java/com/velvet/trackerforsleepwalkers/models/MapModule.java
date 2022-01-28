package com.velvet.trackerforsleepwalkers.models;

import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.trackerforsleepwalkers.models.data.FirestoreLocationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {
    @Provides
    @Singleton
    FirebaseFirestore providesFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    @Singleton
    FirestoreLocationRepository FirestoreLocationRepository() {
        return new FirestoreLocationRepository();
    }

}
