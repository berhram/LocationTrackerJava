package com.velvet.trackerforsleepwalkers.models.preferences;

public interface PreferenceProvider {
    void put(String key, String value);

    String get(String key, String value);
}
