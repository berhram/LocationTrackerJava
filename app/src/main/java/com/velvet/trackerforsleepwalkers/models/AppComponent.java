package com.velvet.trackerforsleepwalkers.models;

import com.velvet.trackerforsleepwalkers.models.auth.FirebaseAuthNetwork;
import com.velvet.trackerforsleepwalkers.models.data.FirestoreLocationRepository;
import com.velvet.trackerforsleepwalkers.ui.login.LoginViewModelFactory;
import com.velvet.trackerforsleepwalkers.ui.map.MapViewModelFactory;
import com.velvet.trackerforsleepwalkers.ui.passwordrecovery.PasswordRecoveryViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class, MapModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);

    void inject(FirestoreLocationRepository repository);

    void inject(MapViewModelFactory factory);
}
