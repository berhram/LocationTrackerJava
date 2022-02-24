package com.velvet.core.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.velvet.core.models.cache.LocationCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.core.models.location.LocationEmitter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LocationEmitter provideEmitter();

    FirebaseAuthNetwork provideAuthRepo();

    LocationCache provideMessageCache();

    Context provideAppContext();

    LocationNetwork provideLocationNetwork();

    WorkManager provideWM();
}
