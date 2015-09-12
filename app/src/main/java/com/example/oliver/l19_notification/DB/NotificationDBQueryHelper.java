package com.example.oliver.l19_notification.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Pair;

import com.example.oliver.l19_notification.Constants;
import com.example.oliver.l19_notification.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliver on 11.09.15.
 */
public class NotificationDBQueryHelper {
    private Context mContext;
    private NotificationDBHelper mDBHelper;
    private SQLiteDatabase mDataBase;

    public NotificationDBQueryHelper(Context context) {
        mContext = context;
    }

    public void open(){
        mDBHelper = new NotificationDBHelper(mContext);
        mDataBase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        if (mDBHelper != null) mDBHelper.close();
    }

    /**
     * Clear base
     */
    public void clearAll() {
        mDataBase.delete(NotificationDBHelper.NOTIFICATION_TABLE_NAME, null, null);
    }
    /**
     * Insert given pair into base
     * @param _pair NotificationCompat.Builder and his ID that  will be inserted
     */
    public void insert(Pair<Integer, NotificationCompat.Builder> _pair) {
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(NotificationDBHelper.NOTIFICATION_ID             , _pair.first);
            cv.put(NotificationDBHelper.NOTIFICATION_CONTENT_TEXT   , _pair.second.mContentText.toString());
            cv.put(NotificationDBHelper.NOTIFICATION_TITLE          , _pair.second.mContentTitle.toString());
            cv.put(NotificationDBHelper.NOTIFICATION_SUBTITLE       , _pair.second.mSubText.toString());
            cv.put(NotificationDBHelper.NOTIFICATION_TICKET_TEXT    , _pair.second.mNotification.tickerText.toString());

            mDataBase.insert(NotificationDBHelper.NOTIFICATION_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, "inserted id: " + _pair.first + ", builder: " + _pair.second.toString());
        }
    }


    /**
     * Get all records from data base
     * @return list of pair<Integer, NotificationCompat.Builder>
     */
    public List<Pair<Integer, NotificationCompat.Builder>> getItems() {

        List<Pair<Integer, NotificationCompat.Builder>>  result = new ArrayList<>();

        Cursor c = mDataBase.query(NotificationDBHelper.NOTIFICATION_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (c != null) {
            while (c.moveToNext()) {

                Integer notification_ID             = c.getInt(c.getColumnIndex(NotificationDBHelper.NOTIFICATION_ID));
                String  notification_content_text   = c.getString(c.getColumnIndex(NotificationDBHelper.NOTIFICATION_CONTENT_TEXT));
                String  notification_title          = c.getString(c.getColumnIndex(NotificationDBHelper.NOTIFICATION_TITLE));
                String  notification_subtitle       = c.getString(c.getColumnIndex(NotificationDBHelper.NOTIFICATION_SUBTITLE));
                String  notification_ticket_text    = c.getString(c.getColumnIndex(NotificationDBHelper.NOTIFICATION_TICKET_TEXT));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

                builder.setContentText(notification_content_text)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle(notification_title)
                        .setSubText(notification_subtitle)
                        .setTicker(notification_ticket_text)
                        .setVibrate(new long[]{0, 100, 200, 300})
                        .setSound(Uri.parse("android.resource://" + Constants.PACKAGE_NAME + "/" + R.raw.notif_sound));

                result.add(new Pair<Integer, NotificationCompat.Builder>(notification_ID, builder));
                Log.d(Constants.TAG, "get " + notification_ID + " builder: " + builder.toString());
            }

            c.close();
        }
        Log.d(Constants.TAG, "------------get from DB :" + result.size() + " items");
        return result;
    }


    public void delete(Integer _notificationID) {

        mDataBase.beginTransaction();
        try {

            int deleted = mDataBase.delete(NotificationDBHelper.NOTIFICATION_TABLE_NAME,
                    NotificationDBHelper.NOTIFICATION_ID + " = ? ",
                    new String[]{String.valueOf(_notificationID)});

            mDataBase.setTransactionSuccessful();
            Log.d(Constants.TAG, "Deleted: " + deleted + " id:" + _notificationID);
        } finally {
            mDataBase.endTransaction();
        }
    }
}
