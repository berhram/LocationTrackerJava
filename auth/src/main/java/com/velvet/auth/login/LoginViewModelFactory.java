package com.velvet.auth.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    FirebaseAuthNetwork repository;

    public LoginViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LoginViewModel.class) {
            return (T) new LoginViewModel(repository);
        }
        return null;
    }
}
