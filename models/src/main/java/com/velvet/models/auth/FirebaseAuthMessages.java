package com.velvet.models.auth;

public class FirebaseAuthMessages {

    // classes used to communicate with auth repo
    public static class RecoveryParams {
        private final String type;
        private String email;
        private String newPassword;
        private String code;

        public RecoveryParams(String code, String newPassword, String type) {
            this.newPassword = newPassword;
            this.code = code;
            this.type = type;
        }

        public RecoveryParams(String email, String type) {
            this.email = email;
            this.type = type;
        }

        public String getType() {
            return type;
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
        private final boolean success;
        private final String id;

        public RecoveryResult(boolean success, String id) {
            this.success = success;
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static class AuthParams {
        private final String email;
        private final String password;
        private final String type;

        public AuthParams(String email, String password, String type) {
            this.email = email;
            this.password = password;
            this.type = type;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getType() {
            return type;
        }
    }
}
