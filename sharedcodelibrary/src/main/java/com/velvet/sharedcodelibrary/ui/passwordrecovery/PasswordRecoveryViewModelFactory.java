package com.velvet.sharedcodelibrary.ui.passwordrecovery;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.trackerforsleepwalkers.App;
import com.velvet.trackerforsleepwalkers.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class PasswordRecoveryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    FirebaseAuthNetwork repository;

    public PasswordRecoveryViewModelFactory() {
        App.getInstance().getComponent().inject(this);
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
