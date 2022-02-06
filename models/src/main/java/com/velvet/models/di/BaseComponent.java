package com.velvet.models.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
interface BaseComponent {
    void inject(Application application);


}
