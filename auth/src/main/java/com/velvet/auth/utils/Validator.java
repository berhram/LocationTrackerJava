package com.velvet.auth.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Validator {
    public static boolean validatePasswordLength(String password) {
        return !TextUtils.isEmpty(password) && (password.length() >= 6);
    }

    public static boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
