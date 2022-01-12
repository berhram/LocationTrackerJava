package com.velvet.trackerforsleepwalkers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.velvet.trackerforsleepwalkers.ui.login.LoginFragment;

public class AppActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    public AppActivity() {
        super(R.layout.activity_main);
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
    }
}
