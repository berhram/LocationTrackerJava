package com.velvet.models.di;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {BaseModule.class})
public interface BaseComponent {
    void inject(FirebaseAuthNetwork firebaseAuthNetwork);

    void inject(ViewModel viewModel);
}
