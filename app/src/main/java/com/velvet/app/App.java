package com.velvet.app;

import android.app.Application;

import com.velvet.core.di.CoreComponent;
import com.velvet.core.di.CoreComponentProvider;
import com.velvet.core.di.CoreModule;
import com.velvet.core.di.DaggerCoreComponent;

public class App extends Application implements CoreComponentProvider {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private CoreComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerCoreComponent.builder().coreModule(new CoreModule(this)).build();
    }

    @Override
    public CoreComponent provideCoreComponent() {
        return component;
    }
}
