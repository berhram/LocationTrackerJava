package com.velvet.childapp;

import android.app.Application;

import com.velvet.sharedmodule.AppComponent;
import com.velvet.sharedmodule.DaggerAppComponent;
import com.velvet.sharedmodule.LoginModule;

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
