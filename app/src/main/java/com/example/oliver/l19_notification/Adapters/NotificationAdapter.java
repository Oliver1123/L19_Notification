package com.example.oliver.l19_notification.Adapters;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oliver.l19_notification.Constants;
import com.example.oliver.l19_notification.NotificationActionListener;
import com.example.oliver.l19_notification.R;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 11.09.15.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private Context mContext;
    private List<Pair<Integer, NotificationCompat.Builder>> mData;
    private NotificationActionListener mListener;

    public NotificationAdapter(Context _context, NotificationActionListener _listener,
                               List<Pair<Integer, NotificationCompat.Builder>> _items) {
        mContext = _context;
        mListener = _listener;
        mData = _items;
    }

    public NotificationAdapter(Context _context) {
        mContext = _context;
    }

    public void setDataItems(List<Pair<Integer, NotificationCompat.Builder>> _items) {
        mData = _items;
    }
    public void addDataItemToTop(Pair<Integer, NotificationCompat.Builder> _pair) {
        if (_pair.first != null)
            mData.add(0, _pair);
    }

    public void addDataItemToEnd(Pair<Integer, NotificationCompat.Builder> _pair) {
        if (_pair.first != null)
            mData.add(_pair);
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup _viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(_viewGroup.getContext())
                .inflate(R.layout.notification_item, _viewGroup, false);

        return new NotificationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder _holder, int position) {
        _holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return (mData == null)? 0: mData.size();
    }



   class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mSubTitle, mText, mTicket;
        private Pair<Integer, NotificationCompat.Builder> mCurrentItem;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            mTitle      = (TextView) itemView.findViewById(R.id.tvTitle_NI);
            mSubTitle   = (TextView) itemView.findViewById(R.id.tvSubTitle_NI);
            mText       = (TextView) itemView.findViewById(R.id.tvText_NI);
            mTicket     = (TextView) itemView.findViewById(R.id.tvTicket_NI);

            itemView.findViewById(R.id.ibDelete).setOnClickListener(this);
        }

        public void onBind(Pair<Integer, NotificationCompat.Builder> _item) {
            mCurrentItem = _item;

            mTitle.setText(mCurrentItem.second.mContentTitle);
            mSubTitle.setText(mCurrentItem.second.mSubText);
            mText.setText(mCurrentItem.second.mContentText);
            mTicket.setText(mCurrentItem.second.mNotification.tickerText);
        }

        @Override
        public void onClick(View v) {
         Log.d(Constants.TAG, "onClick delete pos: " + getAdapterPosition() + ", id: " + mCurrentItem.first);
           mListener.deleteNotification(mCurrentItem.first);
        }
    }
}
