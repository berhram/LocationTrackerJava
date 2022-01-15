package com.velvet.trackerforsleepwalkers;

import android.app.Application;

import com.velvet.trackerforsleepwalkers.ui.login.LoginComponent;
import com.velvet.trackerforsleepwalkers.ui.login.LoginModule;

public class App extends Application {

    private static App instance;

    private LoginComponent loginComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        loginComponent = DaggerLoginComponent.builder().loginModule(new LoginModule()).build();
    }

    public LoginComponent getLoginComponent() {
        return loginComponent;
    }

    public static App getInstance() {
        return instance;
    }

}
