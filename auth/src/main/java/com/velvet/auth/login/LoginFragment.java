package com.velvet.auth.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.auth.databinding.FragmentLoginBinding;
import com.velvet.auth.login.state.LoginViewEffect;
import com.velvet.auth.login.state.LoginViewState;
import com.velvet.core.Values;
import com.velvet.libs.mvi.HostedFragment;

public class LoginFragment extends HostedFragment<LoginViewState,
        LoginContract.ViewModel,
        LoginContract.Host,
        LoginViewEffect,
        LoginContract.View> implements LoginContract.View,
        View.OnClickListener {

    private FragmentLoginBinding binding;

    @Override
    protected LoginContract.ViewModel createModel() {
        return new ViewModelProvider(this, new LoginViewModelFactory(requireActivity())).get(LoginViewModel.class);
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

    private void proceedToPasswordRecovery() {
        if (hasHost()) {
            getFragmentHost().proceedToPasswordRecovery(Values.LOGIN_NAV);
        }
    }

    @Override
    public void proceedToNextScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToNextScreen(Values.LOGIN_NAV);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.signInButton) {
            getModel().signIn(
                    binding.emailInput.getText().toString().trim(),
                    binding.passwordInput.getText().toString().trim()
            );
        } else if (v == binding.signUpButton) {
            getModel().signUp(
                    binding.emailInput.getText().toString().trim(),
                    binding.passwordInput.getText().toString().trim()
            );
        } else if (v == binding.forgotPasswordButton) {
            proceedToPasswordRecovery();
        }
    }

    @Override
    public void setInfoText(int infoText) {
        binding.infoText.setText(infoText);
    }

    @Override
    public void setEmailError(int text) {
        binding.emailInput.setError(getString(text));
    }

    @Override
    public void setPasswordError(int text) {
        binding.passwordInput.setError(getString(text));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
