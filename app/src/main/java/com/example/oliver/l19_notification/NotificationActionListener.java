package com.example.oliver.l19_notification;

import android.support.v4.app.NotificationCompat;
import android.util.Pair;

/**
 * Created by oliver on 12.09.15.
 */
public interface NotificationActionListener {
    void sendNotification(Pair<Integer, NotificationCompat.Builder> _pair);
    void deleteNotification(int id);
}
