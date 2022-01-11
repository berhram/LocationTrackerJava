package com.velvet.trackerforsleepwalkers.ui.loginscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private LoginFragmentBinding binding;
    private LoginViewModel viewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.setup();
        viewModel.getLoginInput().observe(getViewLifecycleOwner(), binding.emailInput::setText);
        viewModel.getPasswordInput().observe(getViewLifecycleOwner(), binding.passwordInput::setText);
        viewModel.getInfoText().observe(getViewLifecycleOwner(), binding.infoText::setText);
        viewModel.getAuthenticationState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (viewModel.getAuthenticationState().getValue()) {
                NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginScreenToMapScreen());
            }
        });
        binding.signInButton.setOnClickListener(this);
        binding.signUpButton.setOnClickListener(this);
        binding.forgotPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            viewModel.login(binding.emailInput.getText().toString(), binding.passwordInput.getText().toString());
        } else if (v.getId() == R.id.sign_up_button) {
            viewModel.register(binding.emailInput.getText().toString(), binding.passwordInput.getText().toString());
            binding.infoText.setText(R.string.success_registration);
        } else {
            NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginScreenToMapScreen());
        }

    }
}
