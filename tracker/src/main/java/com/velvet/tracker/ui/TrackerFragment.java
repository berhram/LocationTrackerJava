package com.velvet.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.Values;
import com.velvet.libs.mvi.HostedFragment;
import com.velvet.tracker.R;
import com.velvet.tracker.databinding.FragmentTrackerBinding;
import com.velvet.tracker.ui.state.TrackerViewEffect;
import com.velvet.tracker.ui.state.TrackerViewState;

public class TrackerFragment extends HostedFragment<TrackerViewState,
        TrackerContract.ViewModel,
        TrackerContract.Host,
        TrackerViewEffect,
        TrackerContract.View> implements TrackerContract.View,
        View.OnClickListener {
    private FragmentTrackerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.startButton.setOnClickListener(this);
        binding.stopButton.setOnClickListener(this);
    }

    @Override
    protected TrackerContract.ViewModel createModel() {
        return new ViewModelProvider(this, new TrackerViewModelFactory(requireActivity())).get(TrackerViewModel.class);
    }

    @Override
    public void proceedToLoginScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToLoginScreen(Values.TRACKER_NAV);
        }
    }

    @Override
    public void setLastLocation(String text) {
        binding.lastLocationTitle.setText(getString(R.string.last_location));
        binding.lastLocation.setText(text);
    }

    @Override
    public void setError(String text) {
        binding.lastLocationTitle.setText(getString(R.string.error));
        binding.lastLocation.setText(text);
    }

    @Override
    public void startService() {
        if (hasHost()) {
            getFragmentHost().startService();
        }
    }

    @Override
    public void stopService() {
        if (hasHost()) {
            getFragmentHost().stopService();
        }
    }

    @Override
    public void onClick(View v) {
        if (binding.startButton == v) {
            startService();
        } else if (binding.stopButton == v) {
            stopService();
        } else if (binding.logoutButton == v) {
            getModel().signOut();
            proceedToLoginScreen();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
