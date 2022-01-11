package com.velvet.trackerforsleepwalkers.ui.passwordrecovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.trackerforsleepwalkers.databinding.SettingsFragmentBinding;

public class PasswordRecoveryFragment extends Fragment {
    private PasswordRecoveryFragmentBinding binding;
    private PasswordRecoveryViewModel viewModel;

    public static PasswordRecoveryFragment newInstance() {
        return new PasswordRecoveryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PasswordRecoveryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PasswordRecoveryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
