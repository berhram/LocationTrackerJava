package com.velvet.tracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.tracker.R;
import com.velvet.tracker.databinding.FragmentTrackerBinding;
import com.velvet.models.preferences.SharedPreferenceProvider;
import com.velvet.mvi.mvi.HostedFragment;

public class TrackerFragment extends HostedFragment<TrackerViewState,
        TrackerContract.ViewModel,
        TrackerContract.Host,
        TrackerViewEffect,
        TrackerContract.View> implements TrackerContract.View,
        View.OnClickListener {
    private FragmentTrackerBinding binding;
    private TrackerViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TrackerViewModel.class);
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
        return null;
    }

    @Override
    public void proceedToMapScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToMapScreen("Tracker");
        }
    }

    @Override
    public void proceedToLoginScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToLoginScreen("Tracker");
        }

    }

    @Override
    public void setSourceSwitch() {
        RadioButton button;
        String defaultSwitch = new SharedPreferenceProvider(getContext()).get("Source", "");
        if (defaultSwitch.equals("Passive")) {
            button = binding.sourceSwitcher.findViewById(R.id.passive);
            button.setChecked(true);
        } else if (defaultSwitch.equals("GPS")) {
            button = binding.sourceSwitcher.findViewById(R.id.gps);
            button.setChecked(true);
        } else if (defaultSwitch.equals("Network")) {
            button = binding.sourceSwitcher.findViewById(R.id.network);
            button.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (hasHost()) {
            if (R.id.gps == binding.sourceSwitcher.getCheckedRadioButtonId()) {
                getFragmentHost().setSource("GPS");
            } else if (R.id.passive == binding.sourceSwitcher.getCheckedRadioButtonId()) {
                getFragmentHost().setSource("Passive");
            } else if (R.id.network == binding.sourceSwitcher.getCheckedRadioButtonId()) {
                getFragmentHost().setSource("Network");
            }
        }
    }
}
