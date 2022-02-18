package com.velvet.auth.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.di.DaggerAuthComponent;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    @Inject
    FirebaseAuthNetwork repository;

    public LoginViewModelFactory(Context ctx) {
        DaggerAuthComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(ctx)).build().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LoginViewModel.class) {
            return (T) new LoginViewModel(repository);
        } else {
            throw new RuntimeException("Unknown class " + modelClass);
        }
    }
}
