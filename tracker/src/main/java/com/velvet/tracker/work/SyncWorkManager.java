package com.velvet.tracker.work;

import com.velvet.core.models.database.remote.LocationNetwork;

public interface SyncWorkManager {
    void doSyncWork(LocationNetwork locationNetwork);
}
