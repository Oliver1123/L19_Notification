package com.example.oliver.l19_notification;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by oliver on 12.09.15.
 */
public class NotificationIDGenerator {
    public static int getNextID(Context _context) {
        SharedPreferences pref =  _context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        int currentID = pref.getInt(Constants.NOTIFICATION_ID_KEY, 0);
        pref.edit().putInt(Constants.NOTIFICATION_ID_KEY, currentID + 1).apply();
        return currentID + 1;
    }
}
