package com.example.oliver.l19_notification.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oliver.l19_notification.Adapters.NotificationAdapter;
import com.example.oliver.l19_notification.DB.NotificationDBQueryHelper;
import com.example.oliver.l19_notification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifListFragment extends Fragment {
    private RecyclerView mRecuclerViewNotification;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NotificationDBQueryHelper mHelper;

    public NotifListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new NotificationDBQueryHelper(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        mHelper.open();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHelper.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notif_list, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.my_toolbar_FNL);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View _rootView) {
        mRecuclerViewNotification= (RecyclerView) _rootView.findViewById(R.id.rvNotification_FNL);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecuclerViewNotification.setLayoutManager(mLayoutManager);

        mAdapter = new NotificationAdapter(getActivity(), mHelper.getItems());
        mRecuclerViewNotification.setAdapter(mAdapter);

    }


}
