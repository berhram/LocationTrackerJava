package com.velvet.app;

import android.app.Application;

import com.velvet.core.di.CoreComponent;
import com.velvet.core.di.CoreComponentProvider;
import com.velvet.core.di.DaggerCoreComponent;


public class App extends Application implements CoreComponentProvider {
    /*
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().coreComponent(provideCoreComponent()).build().inject(this);
    }

     */

    private CoreComponent component;

    @Override
    public CoreComponent provideCoreComponent() {
        if (component == null) {
            component = DaggerCoreComponent.builder().build();
        }
        return component;
    }
}
