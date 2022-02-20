package com.velvet.map.di;

import com.velvet.core.di.CoreComponent;
import com.velvet.map.ui.MapViewModelFactory;

import javax.inject.Scope;

import dagger.Component;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;


@Scope
@Retention(AnnotationRetention.SOURCE)
@interface MapScope {
}


@MapScope
@Component(
        dependencies = {CoreComponent.class},
        modules = {MapModule.class}
)
public interface MapComponent {
    void inject(MapViewModelFactory factory);
}
