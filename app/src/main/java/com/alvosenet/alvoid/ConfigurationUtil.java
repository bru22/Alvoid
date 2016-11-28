package com.alvosenet.alvoid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by brucezeng on 11/28/2016.
 */

public class ConfigurationUtil {
    private static final String NAME = "Config";

    private static SharedPreferences sp = null;

    public static void init(Context context) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String _key) {
        if (sp == null || !sp.contains(_key)) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(_key, false);
            edit.commit();
        }

        return sp.getBoolean(_key, false);
    }

    public static String getString(String key) {
        if (sp == null || !sp.contains(key)) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(key, "");
            edit.commit();
        }

        return sp.getString(key, "");
    }

    public static void setBoolean(String key, boolean value) {
        if (key == null) {
            return;
        }

        if (sp != null) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(key, value);
            edit.commit();
        }
    }

    public static void setString(String key, String value) {
        if (key == null) {
            return;
        }

        if (sp != null) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(key, value);
            edit.commit();
        }
    }
}
