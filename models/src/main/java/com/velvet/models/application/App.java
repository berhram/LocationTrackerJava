package com.velvet.models.application;

import android.app.Application;

import com.velvet.models.di.BaseComponent;
import com.velvet.models.di.DaggerBaseComponent;

public class App extends Application {
    private static App instance;

    private BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public BaseComponent getComponent() {
        if (baseComponent == null) {
            DaggerBaseComponent.factory().create(this);
        }
        return baseComponent;
    }

    public static App getInstance() {
        return instance;
    }
}
