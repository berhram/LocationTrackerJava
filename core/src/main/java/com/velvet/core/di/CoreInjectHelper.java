package com.velvet.core.di;

import android.content.Context;

public class CoreInjectHelper {
    static public CoreComponent provideCoreComponent(Context appContext) {
        if (appContext instanceof CoreComponentProvider) {
            return ((CoreComponentProvider) appContext).provideCoreComponent();
        } else {
            throw new IllegalStateException("Context doesn't implement CoreComponentProvider");
        }
    }
}
