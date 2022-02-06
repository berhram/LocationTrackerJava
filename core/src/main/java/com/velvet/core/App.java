package com.velvet.core;

import android.app.Application;

public class App  extends Application implements AppComponentProvider {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    @Override
    public AppComponent provideAppComponent() {
        return appComponent;
    }

    public static App getInstance() {
        return instance;
    }
}
