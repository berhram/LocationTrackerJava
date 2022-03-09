package com.velvet.core.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.core.models.cache.Cache;
import com.velvet.core.models.database.local.LocalRepository;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.core.models.location.LocationEmitter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LocationEmitter provideEmitter();

    AuthNetwork provideAuthRepo();

    Cache provideCache();

    Context provideAppContext();

    LocationNetwork provideLocationNetwork();

    LocalRepository provideLocalRepository();

    WorkManager provideWM();
}
