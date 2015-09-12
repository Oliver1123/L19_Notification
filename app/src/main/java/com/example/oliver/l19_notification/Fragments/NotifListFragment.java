package com.example.oliver.l19_notification.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.oliver.l19_notification.Adapters.NotificationAdapter;
import com.example.oliver.l19_notification.Constants;
import com.example.oliver.l19_notification.DB.NotificationDBQueryHelper;
import com.example.oliver.l19_notification.Dialog.NotificationDialog;
import com.example.oliver.l19_notification.NotificationActionListener;
import com.example.oliver.l19_notification.NotificationIDGenerator;
import com.example.oliver.l19_notification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifListFragment extends Fragment implements NotificationActionListener {
    private static final int NOTIFICATION_DIALOG_REQUEST_CODE = 222;
    private RecyclerView mRecuclerViewNotification;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NotificationDBQueryHelper mHelper;

    public NotifListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mHelper = new NotificationDBQueryHelper(getActivity());
        mHelper.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHelper.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                getActivity().finish();
                return true;
            case R.id.action_addNotification:
                NotificationDialog dialog = new NotificationDialog();
                dialog.setTargetFragment(this, NOTIFICATION_DIALOG_REQUEST_CODE);
                dialog.show(getActivity().getFragmentManager(), "");
                return true;

            case R.id.action_sync:
                sync();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sync() {
        mAdapter.setDataItems(mHelper.getItems());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notif_list, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(View _rootView) {
        Toolbar toolbar = (Toolbar) _rootView.findViewById(R.id.my_toolbar_FNL);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecuclerViewNotification= (RecyclerView) _rootView.findViewById(R.id.rvNotification_FNL);
        mRecuclerViewNotification.setLayoutManager(mLayoutManager);

        mAdapter = new NotificationAdapter(getActivity(), this, mHelper.getItems());
        mRecuclerViewNotification.setAdapter(mAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Constants.TAG, "NotifListFragment onActivity Result");
        if (requestCode == NOTIFICATION_DIALOG_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                createNotification(data);
            }
        }
    }

    private void createNotification(Intent data) {
        Log.d(Constants.TAG, "NotifListFragment createNotification");
        String message      = data.getStringExtra(Constants.BUILDER_ARG_MESSAGE);
        String title        = data.getStringExtra(Constants.BUILDER_ARG_TITLE);
        String subtitle     = data.getStringExtra(Constants.BUILDER_ARG_SUBTITLE);
        String tickerText   = data.getStringExtra(Constants.BUILDER_ARG_TICKER_TEXT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true)

                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setVibrate(new long[]{0, 100, 200, 300})
                .setSound(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.notif_sound));

        Pair<Integer, NotificationCompat.Builder> pair = new Pair<>(NotificationIDGenerator.getNextID(getActivity()),
                builder);

//        pair.second.setSubText("Subtitle id: " + pair.first);

        addNotification(pair);
    }

    public void addNotification(Pair<Integer, NotificationCompat.Builder> _pair) {
        mHelper.insert(_pair);
        mAdapter.addDataItem(_pair);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendNotification(Pair<Integer, NotificationCompat.Builder> _pair) {
        Log.d(Constants.TAG, "NotifListFragment sendNotification id: " + _pair.first + ", builder: " + _pair.second);
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(_pair.first, _pair.second.build());
    }

    @Override
    public void deleteNotification(int id) {
        Log.d(Constants.TAG, "NotifListFragment deleteNotification id: " + id);
        mHelper.delete(id);
        mAdapter.setDataItems(mHelper.getItems());
        mAdapter.notifyDataSetChanged();
    }
}
