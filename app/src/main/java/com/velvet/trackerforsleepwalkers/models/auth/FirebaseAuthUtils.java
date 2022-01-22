package com.velvet.trackerforsleepwalkers.models.auth;

public class FirebaseAuthUtils {
    //classes used to communicate with auth repo
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
}
