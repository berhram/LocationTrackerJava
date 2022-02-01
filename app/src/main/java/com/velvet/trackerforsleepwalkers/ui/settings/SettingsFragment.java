package com.velvet.trackerforsleepwalkers.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.databinding.FragmentSettingsBinding;
import com.velvet.trackerforsleepwalkers.models.preferences.SharedPreferenceProvider;
import com.velvet.trackerforsleepwalkers.mvi.HostedFragment;

public class SettingsFragment extends HostedFragment<SettingsViewState,
        SettingsContract.ViewModel,
        SettingsContract.Host,
        SettingsViewEffect,
        SettingsContract.View> implements SettingsContract.View,
        View.OnClickListener {
    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected SettingsContract.ViewModel createModel() {
        return null;
    }

    @Override
    public void proceedToMapScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToMapScreen("Settings");
        }
    }

    @Override
    public void proceedToLoginScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToLoginScreen("Settings");
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
