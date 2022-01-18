package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.trackerforsleepwalkers.AppComponent;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppComponent appComponent;

    public LoginViewModelFactory(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LoginViewModel.class) {
            return (T) new LoginViewModel();
        }
        return null;
    }
}
