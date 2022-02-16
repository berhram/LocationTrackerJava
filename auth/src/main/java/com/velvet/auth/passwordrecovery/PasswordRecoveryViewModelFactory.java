package com.velvet.auth.passwordrecovery;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.di.DaggerRecoveryComponent;
import com.velvet.auth.di.RecoveryComponent;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.auth.FirebaseAuthNetwork;

import javax.inject.Inject;

public class PasswordRecoveryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    FirebaseAuthNetwork repository;

    public PasswordRecoveryViewModelFactory(Context context) {
        DaggerRecoveryComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(context)).build().inject(this);
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
