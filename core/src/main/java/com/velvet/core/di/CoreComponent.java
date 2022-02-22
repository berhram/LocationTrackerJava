package com.velvet.core.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.database.local.LocalStorage;
import com.velvet.core.models.database.local.LocalStorageImpl;
import com.velvet.core.models.database.remote.FirebaseRemoteDatabase;
import com.velvet.core.models.database.remote.RemoteDatabase;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.models.location.receiver.LocationReceiver;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LocationReceiver provideLocationReceiver();

    LocationEmitter provideEmitter();

    FirebaseAuthNetwork provideAuthRepo();

    MessageCache provideMessageCache();

    Context provideAppContext();

    LocalStorage provideStorage();

    RemoteDatabase provideRemoteDB();

    WorkManager provideWM();
}
