package com.velvet.tracker.model.work;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SyncWorkManagerImpl implements SyncWorkManager {
    private final WorkManager workManager;
    private final Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();
    private final OneTimeWorkRequest sync = new OneTimeWorkRequest.Builder(SyncWorker.class)
            .setConstraints(constraints)
            .build();

    public SyncWorkManagerImpl(WorkManager workManager) {
        this.workManager = workManager;
    }

    public void scheduleSyncTask() {
        workManager.enqueue(sync).getResult().isDone();
    }
}
