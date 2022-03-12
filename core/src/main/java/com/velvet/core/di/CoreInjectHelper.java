package com.velvet.core.di;

import android.content.Context;

public class CoreInjectHelper {

    static public CoreComponent provideCoreComponent(Context appContext) {
        Context ctx = appContext;
        int tryouts = 0;
        do {
            if (ctx instanceof CoreComponentProvider) {
                return ((CoreComponentProvider) ctx).provideCoreComponent();
            } else {
                ctx = ctx.getApplicationContext();
            }
            tryouts++;
        } while (tryouts < 5);
        throw new RuntimeException("Can't find CoreComponentProvider in supplied contexts");
    }
}
