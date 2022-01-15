package com.velvet.trackerforsleepwalkers;

import android.app.Application;

public class App extends Application {

    private static App instance;

    private AppComponent loginComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        loginComponent = DaggerAppComponent.builder().loginModule(new AppModule()).build();
    }

    public AppComponent getLoginComponent() {
        return loginComponent;
    }

    public static App getInstance() {
        return instance;
    }

}
