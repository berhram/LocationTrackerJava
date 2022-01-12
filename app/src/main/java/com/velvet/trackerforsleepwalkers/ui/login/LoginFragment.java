package com.velvet.trackerforsleepwalkers.ui.login;

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
import com.velvet.trackerforsleepwalkers.databinding.FragmentLoginBinding;
import com.velvet.trackerforsleepwalkers.mvi.MviView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginFragment extends Fragment implements View.OnClickListener, MviView {
    private FragmentLoginBinding binding;
    private LoginViewModel mViewModel;
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.setup();
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
        mDisposables.add(mViewModel.states().);
        mViewModel.getLoginInput().observe(getViewLifecycleOwner(), binding.emailInput::setText);
        mViewModel.getPasswordInput().observe(getViewLifecycleOwner(), binding.passwordInput::setText);
        mViewModel.getInfoText().observe(getViewLifecycleOwner(), binding.infoText::setText);
        mViewModel.getAuthenticationState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (mViewModel.getAuthenticationState().getValue()) {
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
            mViewModel.login(binding.emailInput.getText().toString(), binding.passwordInput.getText().toString());
        } else if (v.getId() == R.id.sign_up_button) {
            mViewModel.register(binding.emailInput.getText().toString(), binding.passwordInput.getText().toString());
            binding.infoText.setText(R.string.success_registration);
        } else {
            NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginScreenToMapScreen());
        }
    }
}
