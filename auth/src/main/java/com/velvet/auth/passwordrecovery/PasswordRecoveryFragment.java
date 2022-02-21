package com.velvet.auth.passwordrecovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.databinding.FragmentPasswordRecoveryBinding;
import com.velvet.auth.passwordrecovery.state.PasswordRecoveryViewEffect;
import com.velvet.auth.passwordrecovery.state.PasswordRecoveryViewState;
import com.velvet.libs.mvi.HostedFragment;

public class PasswordRecoveryFragment extends HostedFragment<PasswordRecoveryViewState, PasswordRecoveryContract.ViewModel, PasswordRecoveryContract.Host, PasswordRecoveryViewEffect, PasswordRecoveryContract.View> implements PasswordRecoveryContract.View, View.OnClickListener {
    private FragmentPasswordRecoveryBinding binding;

    @Override
    protected PasswordRecoveryContract.ViewModel createModel() {
        return new ViewModelProvider(this, new PasswordRecoveryViewModelFactory(requireActivity())).get(PasswordRecoveryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.changePasswordButton.setOnClickListener(this);
        binding.sendCodeButton.setOnClickListener(this);
    }

    @Override
    public void proceedToLoginScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToLoginScreen("Password recovery");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.sendCodeButton) {
            getModel().requestCode(binding.emailInput.getText().toString().trim());
        } else if (v == binding.changePasswordButton) {
            getModel().checkCode(
                    binding.recoveryCode.getText().toString().trim(),
                    binding.newPassword.getText().toString().trim()
            );
        }
    }

    @Override
    public void setEmailError(int text) {
        binding.emailInput.setError(getString(text));

    }

    @Override
    public void setPasswordError(int text) {
        binding.newPassword.setError(getString(text));
    }

    @Override
    public void setInfoText(int infoText) {
        binding.infoText.setText(infoText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
