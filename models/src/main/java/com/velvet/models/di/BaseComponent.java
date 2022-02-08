package com.velvet.models.di;

import android.content.Context;

import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {BaseModule.class, CacheModule.class, NetworkModule.class})
public interface BaseComponent {
    @Component.Factory
    interface Factory {
        BaseComponent create(@BindsInstance Context context);
    }

    void inject(FirebaseAuthNetwork network);
}
