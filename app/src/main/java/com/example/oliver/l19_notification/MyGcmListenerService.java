package com.example.oliver.l19_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by oliver on 11.09.15.
 */
public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(Constants.TAG, "Got message: " + data.getString("message"));
        showNotification(this, data);
    }

    public void showNotification(Context context, final Bundle bundle) {
        String message      = bundle.getString("message");
        String title        = bundle.getString("title");
        String subtitle     = bundle.getString("subtitle");
        String tickerText   = bundle.getString("tickerText");
        int vibrate         = Integer.valueOf(bundle.getString("vibrate"));
        int sound           = Integer.valueOf(bundle.getString("sound"));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);
        Notification notification = builder.build();

        Log.d(Constants.TAG, "contentText: " + builder.mContentText);
        Log.d(Constants.TAG, "title: " + builder.mContentTitle);
        Log.d(Constants.TAG, "subtitle: " + builder.mSubText);
        Log.d(Constants.TAG, "tickerText: " + builder.mNotification.tickerText);

        if (vibrate == 1) builder.setVibrate(new long[]{0, 100, 200, 300});
//        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.smb_jump_small));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1234, builder.build());
    }

}
