package com.velvet.trackerforsleepwalkers.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.trackerforsleepwalkers.databinding.FragmentLoginBinding;
import com.velvet.trackerforsleepwalkers.mvi.HostedFragment;

public class LoginFragment extends HostedFragment<LoginViewState, LoginContract.ViewModel, LoginContract.Host> implements LoginContract.View, View.OnClickListener {
    private FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected LoginContract.ViewModel createModel() {
        return new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signInButton.setOnClickListener(this);
        binding.signUpButton.setOnClickListener(this);
        binding.forgotPasswordButton.setOnClickListener(this);

    }

    @Override
    public void proceedToPasswordRecovery() {
        if (hasHost()) {
            getFragmentHost().proceedToPasswordRecovery("Login");
        }
    }

    @Override
    public void proceedToNextScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToNextScreen("Login");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.signInButton) {
            getModel().signIn(binding.emailInput.getText().toString().trim(), binding.passwordInput.getText().toString().trim());
        } else if (v == binding.signUpButton) {
            getModel().signUp(binding.emailInput.getText().toString().trim(), binding.passwordInput.getText().toString().trim());
        } else if (v == binding.forgotPasswordButton) {
            proceedToPasswordRecovery();
        }
    }

    @Override
    public void setInfoText(int infoText) {
        binding.infoText.setText(infoText);
    }
}
