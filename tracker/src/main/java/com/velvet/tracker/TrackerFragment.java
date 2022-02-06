package com.velvet.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.mvi.HostedFragment;
import com.velvet.tracker.databinding.FragmentTrackerBinding;

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
    public void onClick(View v) {

    }
}