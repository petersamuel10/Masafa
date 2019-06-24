package com.vavisa.masafah.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.vavisa.masafah.base.BaseApplication;

import java.util.Set;

public class Preferences {

    private static Preferences preferences = null;
    private SharedPreferences.Editor editor;

    private Preferences() {
        editor = getCurrent().edit();
    }

    public static synchronized Preferences getInstance() {
        if (preferences == null) {
            preferences = new Preferences();
        }
        return preferences;
    }

    public Integer getInt(String key){
        return getCurrent().getInt(key, 0);
    }

    public void putInt(String key, Integer value){
        editor.putInt(key, value);
        editor.commit();
    }

    public String getString(String key) {

        return getCurrent().getString(key, null);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public Boolean getBoolean(String key){
        return getCurrent().getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clear(){
        editor.clear().apply();
    }

    public Boolean isHasKey(String key){
        return getCurrent().contains(key);
    }

    private SharedPreferences getCurrent() {
        return BaseApplication.getAppContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
    }
}
