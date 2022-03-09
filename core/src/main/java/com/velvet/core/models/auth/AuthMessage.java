package com.velvet.core.models.auth;

import com.velvet.core.Values;

public class AuthMessage {
    public final String id;
    public final String emailOrCode;
    public final String pwd;

    public AuthMessage(String id, String emailOrCode, String pwd) {
        this.id = id;
        this.emailOrCode = emailOrCode;
        this.pwd = pwd;
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

    public static AuthMessage createCheckCodeAndSetNewPasswordMessage(String code, String newPassword) {
        return new AuthMessage(Values.CHECK, code, newPassword);
    }
}
