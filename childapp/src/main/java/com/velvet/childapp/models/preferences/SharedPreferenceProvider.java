package com.velvet.childapp.models.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceProvider implements PreferenceProvider{
    private final SharedPreferences preferences;

    public SharedPreferenceProvider(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private SharedPreferences.Editor getEditor(){
        return preferences.edit();
    }

    @Override
    public void put(String key, String value){
        getEditor().putString(key, value).apply();
    }

    @Override
    public String get(String key, String defValue){
        return preferences.getString(key, defValue);
    }
}
