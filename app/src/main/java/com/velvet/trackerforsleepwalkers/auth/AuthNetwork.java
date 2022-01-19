package com.velvet.trackerforsleepwalkers.auth;

public interface AuthNetwork {
    boolean checkIfUserLoggedIn();
    int login(String email, String password);
    int register(String email, String password);
    int getInfoText();
}
