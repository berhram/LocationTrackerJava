package com.velvet.tracker.di;

import com.velvet.core.di.CoreComponent;
import com.velvet.tracker.TrackerViewModelFactory;
import com.velvet.tracker.services.controller.TrackerControllerFactory;
import com.velvet.tracker.services.TrackerService;

import javax.inject.Scope;

import dagger.Component;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;


@Scope
@Retention(AnnotationRetention.SOURCE)
@interface TrackerScope {
}


@TrackerScope
@Component(
        dependencies = {CoreComponent.class},
        modules = {TrackerModule.class}
)
public interface TrackerComponent {
    void inject(TrackerService factory);

    void inject(TrackerViewModelFactory factory);

    void inject(TrackerControllerFactory controllerFactory);
}
