package com.velvet.core.models.auth;

import com.velvet.core.Values;

public class AuthMessage {
    private final String id;
    private final String firstParam;
    private final String secondParam;

    public AuthMessage(String id, String firstParam, String secondParam) {
        this.id = id;
        this.firstParam = firstParam;
        this.secondParam = secondParam;
    }

    public static AuthMessage createLoginMessage(String email, String password) {
        return new AuthMessage(Values.LOGIN, email, password);
    }

    public static AuthMessage createRegisterMessage(String email, String password) {
        return new AuthMessage(Values.REGISTER, email, password);
    }

    public static AuthMessage createRequestCodeMessage(String email) {
        return new AuthMessage(Values.REQUEST, email, "");
    }

    public static AuthMessage createCheckCodeAndSetNewPassowordMessage(String code, String newPassword) {
        return new AuthMessage(Values.CHECK, code, newPassword);
    }

    public String getId() {
        return id;
    }

    public String getFirstParam() {
        return firstParam;
    }

    public String getSecondParam() {
        return secondParam;
    }
}
