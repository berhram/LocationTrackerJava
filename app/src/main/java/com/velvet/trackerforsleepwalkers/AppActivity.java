package com.velvet.trackerforsleepwalkers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
    
import android.os.Bundle;

import com.velvet.trackerforsleepwalkers.ui.loginscreen.LoginFragment;
import com.velvet.trackerforsleepwalkers.ui.mapscreen.MapFragment;

public class AppActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    public AppActivity() {
        super(R.layout.app_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainerView, LoginFragment.newInstance(), null)
                    .commit();
        }
        //TODO check if user is sign in already
    }


}