package com.velvet.app.di;

import com.velvet.app.App;
import com.velvet.core.di.CoreComponent;
import com.velvet.libs.di.scopes.AppScope;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {AndroidInjectionModule.class,
        AppModule.class},
        dependencies = {CoreComponent.class})
@AppScope
interface AppComponent extends AndroidInjector<App> {
    void inject(App app);
}




