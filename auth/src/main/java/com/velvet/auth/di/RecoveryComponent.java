package com.velvet.auth.di;

import com.velvet.auth.passwordrecovery.PasswordRecoveryViewModelFactory;
import com.velvet.core.di.CoreComponent;
import com.velvet.libs.di.scopes.FeatureScope;

import dagger.Component;

@FeatureScope
@Component(dependencies = {CoreComponent.class})
public interface RecoveryComponent {
    void inject(PasswordRecoveryViewModelFactory factory);
}
