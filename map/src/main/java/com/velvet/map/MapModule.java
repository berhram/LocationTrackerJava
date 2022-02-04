package com.velvet.map;

import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.models.models.data.FirestoreLocationRepository;

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
