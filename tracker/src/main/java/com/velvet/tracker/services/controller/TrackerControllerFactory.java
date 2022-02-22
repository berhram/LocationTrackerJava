package com.velvet.tracker.services.controller;

import android.content.Context;

import com.velvet.core.cache.MessageCache;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.tracker.di.DaggerTrackerComponent;
import com.velvet.tracker.di.TrackerModule;
import com.velvet.tracker.work.SyncWorkManager;

import javax.inject.Inject;

public class TrackerControllerFactory implements ControllerFactory<TrackerController> {
    @Inject
    SyncWorkManager workManager;
    @Inject
    LocationNetwork locationRepo;
    @Inject
    MessageCache cache;
    @Inject
    LocationEmitter emitter;

    public TrackerControllerFactory(Context appCtx) {
        DaggerTrackerComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(appCtx)).trackerModule(new TrackerModule(appCtx)).build().inject(this);
    }

    @Override
    public TrackerController create() {
        return new TrackerController(workManager, locationRepo, cache, emitter);
    }
}
