package com.velvet.tracker.di;

import com.velvet.core.di.CoreComponent;
import com.velvet.libs.di.scopes.FeatureScope;
import com.velvet.tracker.services.TrackerService;

import dagger.Component;

@Component(dependencies = {CoreComponent.class})
@FeatureScope
public interface TrackerServiceComponent {
    void inject(TrackerService service);
}
