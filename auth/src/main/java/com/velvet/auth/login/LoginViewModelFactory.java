package com.velvet.auth.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.AuthModule;
import com.velvet.auth.DaggerAuthComponent;
import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    FirebaseAuthNetwork repository;

    public LoginViewModelFactory() {
        DaggerAuthComponent.builder().authModule(new AuthModule()).build().inject(this);
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
