package com.example.oliver.l19_notification.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oliver on 11.09.15.
 */
public class NotificationDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "NotificationDB.";

    private static final int DB_VERSION = 1;

    public static final String NOTIFICATION_TABLE_NAME = "markers";


    public static final String NOTIFICATION_ID              = "_id";
    public static final String NOTIFICATION_CONTENT_TEXT    = "notification_contentText";
    public static final String NOTIFICATION_TITLE           = "notification_title";
    public static final String NOTIFICATION_SUBTITLE        = "notification_subtitle";
    public static final String NOTIFICATION_TICKET_TEXT     = "notification_tickerText";


    private static final String NOTIFICATION_TABLE_CREATE =
            "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" +
                    NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTIFICATION_CONTENT_TEXT + " TEXT, " +
                    NOTIFICATION_TITLE + " TEXT, " +
                    NOTIFICATION_SUBTITLE + " TEXT, " +
                    NOTIFICATION_TICKET_TEXT + " TEXT);";

    public NotificationDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTIFICATION_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
