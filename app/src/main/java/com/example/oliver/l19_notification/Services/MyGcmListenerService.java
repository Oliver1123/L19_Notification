package com.example.oliver.l19_notification.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Pair;

import com.example.oliver.l19_notification.Constants;
import com.example.oliver.l19_notification.DB.NotificationDBQueryHelper;
import com.example.oliver.l19_notification.NotificationIDGenerator;
import com.example.oliver.l19_notification.R;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by oliver on 11.09.15.
 */
public class MyGcmListenerService extends GcmListenerService {


    public static final String ACTION_ADD_NOTIFICATION = "addNotification";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(Constants.TAG, "Got message: " + data.getString("message"));
        showNotification(this, data);
    }

    public void showNotification(Context context, final Bundle bundle) {
        String message      = bundle.getString(Constants.BUILDER_ARG_MESSAGE, "");
        String title        = bundle.getString(Constants.BUILDER_ARG_TITLE, "");
        String subtitle     = bundle.getString(Constants.BUILDER_ARG_SUBTITLE, "");
        String tickerText   = bundle.getString(Constants.BUILDER_ARG_TICKER_TEXT, "");
        int vibrate         = Integer.valueOf(bundle.getString(Constants.BUILDER_ARG_VIBRATE, "0"));
        int sound           = Integer.valueOf(bundle.getString(Constants.BUILDER_ARG_SOUND, "0"));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if (vibrate == 1) builder.setVibrate(new long[]{0, 100, 200, 300});
        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notif_sound));

        Pair<Integer, NotificationCompat.Builder> pair = new Pair<>(NotificationIDGenerator.getNextID(this),
                                                                    builder);

        pair.second.setTicker("Notification id: " + pair.first);
        pair.second.setSubText("Subtitle id: " + pair.first);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(pair.first, pair.second.build());

        NotificationDBQueryHelper helper = new NotificationDBQueryHelper(context);
        helper.open();
        helper.insert(pair);
        helper.close();
        // TODO: send to list that db changed
        Intent intent = new Intent(ACTION_ADD_NOTIFICATION);
        sendBroadcast(intent);


    }

}
