package com.app.tcs.sanbot.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharepreferenceKeystore {
    private static SharepreferenceKeystore store;
    private SharedPreferences SP;
    private static String filename = "SIA_KEYS";

    private SharepreferenceKeystore(Context context) {
        SP = context.getApplicationContext().getSharedPreferences(filename, 0);
    }

    public static SharepreferenceKeystore getInstance(Context context) {
        if (store == null) {
            store = new SharepreferenceKeystore(context);
        }
        return store;
    }

    public void updateKey(String key, String value) {
        Editor editor;
        editor = SP.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public String getKey(String key) {
        return SP.getString(key, null);
    }

    public void updateBoolean(String key, boolean value) {
        Editor editor;
        editor = SP.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public boolean getBoolean(String key) {
        return SP.getBoolean(key, true);
    }

    public void clear() {
        Editor editor;
        editor = SP.edit();
        editor.clear();
        editor.commit();
    }

    public void remove() {
        Editor editor;
        editor = SP.edit();
        editor.remove(filename);
        editor.commit();
    }
}
