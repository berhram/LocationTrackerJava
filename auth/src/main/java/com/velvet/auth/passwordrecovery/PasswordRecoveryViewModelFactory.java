package com.velvet.auth.passwordrecovery;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.di.DaggerAuthComponent;
import com.velvet.models.application.App;
import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class PasswordRecoveryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    FirebaseAuthNetwork repository;

    public PasswordRecoveryViewModelFactory() {
        DaggerAuthComponent.factory().create(App.getInstance().getComponent()).inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PasswordRecoveryViewModel.class) {
            return (T) new PasswordRecoveryViewModel(repository);
        }
        return null;
    }
}
