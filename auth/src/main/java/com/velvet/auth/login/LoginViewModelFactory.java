package com.velvet.auth.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.di.DaggerLoginComponent;
import com.velvet.auth.di.LoginComponent;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    FirebaseAuthNetwork repository;

    public LoginViewModelFactory(Context context) {
        DaggerLoginComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(context)).build().inject(this);
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
