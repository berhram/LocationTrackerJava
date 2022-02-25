package com.velvet.tracker.model.work;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.velvet.core.Values;
import com.velvet.core.models.database.remote.LocationNetwork;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SyncWorkManagerImpl implements SyncWorkManager {
    private final WorkManager workManager;
    BehaviorSubject<Boolean> workResult = BehaviorSubject.create();
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();
    OneTimeWorkRequest sync = new OneTimeWorkRequest.Builder(SyncWorker.class)
            .setConstraints(constraints)
            .build();

    public SyncWorkManagerImpl(WorkManager workManager) {
        this.workManager = workManager;
    }

    public void syncRepos() {
        workResult.onNext(workManager.enqueue(sync).getResult().isDone());
    }
}
