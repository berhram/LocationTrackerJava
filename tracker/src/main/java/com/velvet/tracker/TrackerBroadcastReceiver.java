package com.velvet.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.velvet.tracker.services.TrackerService;

public class TrackerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, TrackerService.class));
    }
}
