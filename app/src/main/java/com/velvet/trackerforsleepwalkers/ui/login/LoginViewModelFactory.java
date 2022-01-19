package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.App;
import com.velvet.trackerforsleepwalkers.AppComponent;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    AuthRepository repository;

    public LoginViewModelFactory(AppComponent appComponent) {
        App.getInstance().getComponent().inject(this);
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
