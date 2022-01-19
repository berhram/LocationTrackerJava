package com.velvet.trackerforsleepwalkers;

import android.app.Application;

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
