package com.velvet.tracker.model.work;

import com.velvet.core.models.database.remote.LocationNetwork;

public interface SyncWorkManager {
    void doSyncWork(LocationNetwork locationNetwork);
}
