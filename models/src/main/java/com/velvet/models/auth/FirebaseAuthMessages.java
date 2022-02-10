package com.velvet.models.auth;

import com.velvet.models.Values;

public class FirebaseAuthMessages {
    public static class RecoveryParams {
        private final String id;
        private String email;
        private String newPassword;
        private String code;

        public RecoveryParams(String code, String newPassword) {
            this.newPassword = newPassword;
            this.code = code;
            this.id = Values.CHECK;
        }

        public RecoveryParams(String email) {
            this.email = email;
            this.id = Values.REQUEST;
        }

        public boolean isRequest() {
            return id.equals(Values.REQUEST);
        }

        public String getNewPassword() {
            return newPassword;
        }

        public String getCode() {
            return code;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class RecoveryResult {
        private final String id;

        public RecoveryResult(String id) {
            this.id = id;
        }

        public boolean isRequest() {
            return id.equals(Values.REQUEST);
        }
    }

    public static class AuthParams {
        private final String email;
        private final String password;
        private final String id;

        public AuthParams(String email, String password, String id) {
            this.email = email;
            this.password = password;
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public boolean isLogin() {
            return id.equals(Values.LOGIN);
        }
    }
}
