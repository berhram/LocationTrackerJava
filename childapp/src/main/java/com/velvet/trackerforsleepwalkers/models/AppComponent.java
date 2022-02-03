package com.velvet.trackerforsleepwalkers.models;

import com.velvet.sharedmodule.auth.FirebaseAuthNetwork;
import com.velvet.trackerforsleepwalkers.models.data.FirestoreLocationRepository;
import com.velvet.sharedmodule.ui.login.LoginViewModelFactory;
import com.velvet.parentapp.ui.map.MapViewModelFactory;
import com.velvet.sharedmodule.ui.passwordrecovery.PasswordRecoveryViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class, MapModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);

    void inject(FirestoreLocationRepository repository);
}
