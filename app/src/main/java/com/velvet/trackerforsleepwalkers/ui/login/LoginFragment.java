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

import com.jakewharton.rxbinding4.view.RxView;
import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.databinding.FragmentLoginBinding;
import com.velvet.trackerforsleepwalkers.mvi.MviView;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginFragment extends Fragment implements MviView<LoginIntent, LoginViewState> {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //mViewModel.setup();
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
        bind();
    }

    private void bind() {
        disposable.add(viewModel.states().subscribe(this::render));
        viewModel.processIntents(intents());
    }

    @Override
    public Observable<LoginIntent> intents() {
        return Observable.merge(initialIntent(), signInIntent(), signUpIntent(), forgotPasswordIntent());
    }

    private Observable<LoginIntent.InitialIntent> initialIntent() {
        return Observable.just(LoginIntent.InitialIntent.create());
    }

    private Observable<LoginIntent.SignInIntent> signInIntent() {
        return RxView.clicks(binding.signInButton).map(ignored ->
                LoginIntent.SignInIntent.create(
                        binding.emailInput.getText().toString(),
                        binding.passwordInput.getText().toString()));
    }

    private Observable<LoginIntent.SignUpIntent> signUpIntent() {
        return RxView.clicks(binding.signUpButton).map(ignored ->
                LoginIntent.SignUpIntent.create(
                        binding.emailInput.getText().toString(),
                        binding.passwordInput.getText().toString()));
    }

    private Observable<LoginIntent.ForgotPasswordIntent> forgotPasswordIntent() {
        return RxView.clicks(binding.signInButton).map(ignored ->
                LoginIntent.ForgotPasswordIntent.create());
    }

    @Override
    public void render(LoginViewState state) {
        if (state.isSignInSuccess()) {
            NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginScreenToMapScreen());
            return;
        }
        if (state.isPasswordForgotten()) {
            NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginScreenToPassordRecoveryScreen());
            return;
        }
        //if sign in failure or user just register or email/password is incorrect -> show infotext
        binding.infoText.setText(state.infoText());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
