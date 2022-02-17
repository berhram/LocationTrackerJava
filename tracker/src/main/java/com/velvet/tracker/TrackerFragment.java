package com.velvet.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.Values;
import com.velvet.libs.mvi.HostedFragment;
import com.velvet.tracker.databinding.FragmentTrackerBinding;
import com.velvet.tracker.services.TrackerService;
import com.velvet.tracker.state.TrackerViewEffect;
import com.velvet.tracker.state.TrackerViewState;

public class TrackerFragment extends HostedFragment<TrackerViewState,
        TrackerContract.ViewModel,
        TrackerContract.Host,
        TrackerViewEffect,
        TrackerContract.View> implements TrackerContract.View,
        View.OnClickListener {
    private FragmentTrackerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected TrackerContract.ViewModel createModel() {
        return new ViewModelProvider(this).get(TrackerViewModel.class);
    }

    @Override
    public void proceedToLoginScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToLoginScreen("Tracker");
        }
    }

    @Override
    public void setLastLocation(String text) {
        binding.lastLocation5.setText(binding.lastLocation4.getText());
        binding.lastLocation4.setText(binding.lastLocation3.getText());
        binding.lastLocation3.setText(binding.lastLocation2.getText());
        binding.lastLocation2.setText(binding.lastLocation1.getText());
        binding.lastLocation1.setText(text);
    }

    @Override
    public void setStartButtonText(String text) {
        if (Values.START.equals(text)) {
            binding.startButton.setText(R.string.start_button);
        } else if (Values.STOP.equals(text)) {
            binding.startButton.setText(R.string.stop_button);
        }
    }

    @Override
    public void startService() {
        getActivity().startService(new Intent(getActivity(), TrackerService.class));
        setStartButtonText(Values.STOP);
        if (hasHost()) {
            getFragmentHost().startNotification();
        }
    }

    @Override
    public void stopService() {
        getActivity().stopService(new Intent(getActivity(), TrackerService.class));
        setStartButtonText(Values.START);
        if (hasHost()) {
            getFragmentHost().stopNotification();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
