package com.velvet.tracker.work;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.velvet.core.Values;
import com.velvet.core.models.database.remote.LocationNetwork;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SyncWorkManagerImpl {
    private final WorkManager workManager;
    BehaviorSubject<Boolean> workResult = BehaviorSubject.create();
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();

    public SyncWorkManagerImpl(WorkManager workManager) {
        this.workManager = workManager;
    }

    public void doSyncWork(LocationNetwork locationNetwork) {
        //TODO why put incorrect idk yet, will fix it later
        Data workData = new Data.Builder().put(Values.WORK_KEY, locationNetwork).build();
        OneTimeWorkRequest sync = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .setConstraints(constraints)
                .setInputData(workData)
                .build();
        workResult.onNext(workManager.enqueue(sync).getResult().isDone());
    }
}
