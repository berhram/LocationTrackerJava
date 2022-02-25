package com.velvet.tracker.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.velvet.tracker.model.work.SyncWorkManager;
import com.velvet.tracker.model.work.SyncWorkManagerImpl;

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
    SyncWorkManager provideWM() {
        return new SyncWorkManagerImpl(WorkManager.getInstance(appCtx));
    }
}
