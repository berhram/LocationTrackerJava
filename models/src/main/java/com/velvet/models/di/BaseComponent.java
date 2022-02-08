package com.velvet.models.di;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.models.cache.ServiceCache;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {BaseModule.class})
public interface BaseComponent {
    ServiceCache getCache();

    FirebaseAuth getAuth();
}
