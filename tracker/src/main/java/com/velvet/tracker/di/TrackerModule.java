package com.velvet.tracker.di;

import android.content.Context;

import com.velvet.tracker.services.controller.TrackerController;
import com.velvet.tracker.services.controller.TrackerControllerFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class TrackerModule {
    private final Context appCtx;

    public TrackerModule(Context appCtx) {
        this.appCtx = appCtx;
    }

    @Provides
    @TrackerScope
    TrackerController provideController() {
        return new TrackerControllerFactory(appCtx).create();
    }
}
