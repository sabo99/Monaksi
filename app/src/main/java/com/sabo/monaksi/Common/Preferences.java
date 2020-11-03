package com.sabo.monaksi.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    private static SharedPreferences sharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** ID User */
    public static void setIDUser(Context context, int idUser){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putInt(Common.SF_ID_USER, idUser);
        editor.apply();
    }

    public static int getIDUser(Context context){
        return sharedPreferences(context).getInt(Common.SF_ID_USER, 0);
    }

    /** Username */
    public static void setUsername(Context context, String username){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(Common.SF_USERNAME, username);
        editor.apply();
    }

    public static String getUsername(Context context){
        return sharedPreferences(context).getString(Common.SF_USERNAME, "");
    }

    /** Nama */
    public static void setNama(Context context, String nama){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(Common.SF_NAMA, nama);
        editor.apply();
    }

    public static String getNama(Context context){
        return sharedPreferences(context).getString(Common.SF_NAMA, "");
    }

    /** Level */
    public static void setLevel(Context context, String level){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(Common.SF_LEVEL, level);
        editor.apply();
    }

    public static String getLevel(Context context){
        return sharedPreferences(context).getString(Common.SF_LEVEL, "");
    }

    /** Email */
    public static void setEmail(Context context, String email){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(Common.SF_EMAIL, email);
        editor.apply();
    }

    public static String getEmail(Context context){
        return sharedPreferences(context).getString(Common.SF_EMAIL, "");
    }

    /** Last Login */
    public static void setLastLogin(Context context, String lastLogin){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(Common.SF_LAST_LOGIN, lastLogin);
        editor.apply();
    }

    public static String getLastLogin(Context context){
        return sharedPreferences(context).getString(Common.SF_LAST_LOGIN, "");
    }

    /** ID Jabatan User */
    public static void setIDJabatan(Context context, int idJabatan){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putInt(Common.SF_ID_JABATAN, idJabatan);
        editor.apply();
    }

    public static int getIDJabatan(Context context){
        return sharedPreferences(context).getInt(Common.SF_ID_JABATAN, 0);
    }

    /** Logged In Status */
    public static void setLoggedInStatus(Context context, boolean loggedIn){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putBoolean(Common.SF_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return sharedPreferences(context).getBoolean(Common.SF_LOGGED_IN, false);
    }


    /** Clear Share Preferences */
    public static void clearSharePreferences(Context context){
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.remove(Common.SF_USERNAME);
        editor.remove(Common.SF_NAMA);
        editor.remove(Common.SF_LEVEL);
        editor.remove(Common.SF_LAST_LOGIN);
        editor.remove(Common.SF_ID_JABATAN);
        editor.remove(Common.SF_LOGGED_IN);
        editor.apply();
    }
}
