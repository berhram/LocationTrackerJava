package com.velvet.app;

import android.app.Application;

import com.velvet.core.di.CoreComponent;
import com.velvet.core.di.CoreComponentProvider;
import com.velvet.core.di.DaggerCoreComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class App extends Application implements CoreComponentProvider, HasAndroidInjector {
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().coreComponent(provideCoreComponent()).build().inject(this);
    }

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }


    private CoreComponent component;

    @Override
    public CoreComponent provideCoreComponent() {
        if (component == null) {
            component = DaggerCoreComponent.builder().build();
        }
        return component;
    }
}
