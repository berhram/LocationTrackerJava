package com.velvet.app;

import android.app.Application;

import com.velvet.core.di.CoreComponent;
import com.velvet.core.di.CoreComponentProvider;
import com.velvet.core.di.CoreModule;
import com.velvet.core.di.DaggerCoreComponent;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerCoreComponent.builder().coreModule(new CoreModule()).build();
    }
}
