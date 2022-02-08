package com.velvet.models.di;

import android.app.Application;

import com.velvet.models.application.App;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BaseModule {
    @Binds
    @Singleton
    abstract Application bindApplication(App application);
}
