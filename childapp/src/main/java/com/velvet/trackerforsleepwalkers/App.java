package com.velvet.trackerforsleepwalkers;

import android.app.Application;

import com.velvet.trackerforsleepwalkers.models.AppComponent;
import com.velvet.trackerforsleepwalkers.models.DaggerAppComponent;
import com.velvet.trackerforsleepwalkers.models.LoginModule;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().loginModule(new LoginModule()).build();
    }

    public AppComponent getComponent() {
        return appComponent;
    }

    public static App getInstance() {
        return instance;
    }

}
