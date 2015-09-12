package com.example.oliver.l19_notification;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.oliver.l19_notification.Fragments.NotifListFragment;
import com.example.oliver.l19_notification.Services.MyGcmListenerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    protected final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(Constants.TAG, " checkPlayServices: " + checkPlayServices(this));
        getToken();

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NotifListFragment fragment = (NotifListFragment) getFragmentManager().findFragmentById(R.id.notificationListFragment);
                if (intent.getAction().equals(MyGcmListenerService.ACTION_ADD_NOTIFICATION)) {
                    fragment.sync();
                }
            }
        };
        // Create filter
        IntentFilter intFilter = new IntentFilter();
        intFilter.addAction(MyGcmListenerService.ACTION_ADD_NOTIFICATION);
        registerReceiver(br, intFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG, " checkPlayServices: " + checkPlayServices(this));
    }

    private void getToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.d(Constants.TAG, "Token: " + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected static boolean checkPlayServices(Activity activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(Constants.TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }
}
