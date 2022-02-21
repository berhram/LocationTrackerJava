package com.velvet.core.models.database.remote;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.Values;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.core.Single;

public class FirebaseRemoteDatabase implements RemoteDatabase {
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);

    public boolean saveLocation(Location location) {
        Task task = database.child("location").child(sDF.format(new Date(location.getTime()))).push().setValue(location);
        return task.isSuccessful();
    }
}
