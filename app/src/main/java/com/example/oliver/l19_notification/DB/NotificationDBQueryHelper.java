package com.example.oliver.l19_notification.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.oliver.l19_notification.Constants;
import com.example.oliver.l19_notification.Custom.MyEntry;

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
     * Insert given NotificationCompat.Builder with id into base
     * @param _entry NotificationCompat.Builder and his ID that  will be inserted
     */
    public void insert(MyEntry<Integer, NotificationCompat.Builder> _entry) {
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(NotificationDBHelper.NOTIFICATION_ID              , _entry.getKey());
            cv.put(NotificationDBHelper.NOTIFICATION_CONTENT_TEXT    , _entry.getValue().mContentText.toString());
            cv.put(NotificationDBHelper.NOTIFICATION_TITLE           , _entry.getValue().mContentTitle.toString());
            cv.put(NotificationDBHelper.NOTIFICATION_SUBTITLE        , _entry.getValue().mSubText.toString());
            cv.put(NotificationDBHelper.NOTIFICATION_TICKET_TEXT     , _entry.getValue().mNotification.tickerText.toString());

            mDataBase.insert(NotificationDBHelper.NOTIFICATION_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, "inserted " + _entry.toString());
        }
    }


    /**
     * Get all entry from data base
     * @return list of MyEntry<Integer, NotificationCompat.Builder>
     */
    public List<MyEntry<Integer, NotificationCompat.Builder>> getItems() {

        List<MyEntry<Integer, NotificationCompat.Builder>>  result = new ArrayList<>();

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
                        .setContentTitle(notification_title)
                        .setSubText(notification_subtitle)
                        .setTicker(notification_ticket_text);

                MyEntry<Integer, NotificationCompat.Builder> entry = new MyEntry<>(notification_ID, builder);
                result.add(entry);
                Log.d(Constants.TAG, "get " + entry.toString());
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
