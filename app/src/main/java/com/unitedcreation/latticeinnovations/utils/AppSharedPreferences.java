package com.unitedcreation.latticeinnovations.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

    private static final String SHARED_PREF = "shared_preferences";
    private static final String USER_STATUS = "user_status";

    private static SharedPreferences getSharedPreferences (Application application) {
        return application.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    public static boolean isUserLoggedIn (Application application) {
        return getSharedPreferences(application).getBoolean(USER_STATUS, false);
    }

    public static void updateUserStatus (Application application, boolean status) {
        getSharedPreferences(application).edit().putBoolean(USER_STATUS, status).apply();
    }
}
