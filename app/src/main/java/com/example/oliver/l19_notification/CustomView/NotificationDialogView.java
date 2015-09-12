package com.example.oliver.l19_notification.CustomView;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.oliver.l19_notification.R;

/**
 * Created by oliver on 12.09.15.
 */
public class NotificationDialogView extends RelativeLayout {
    private EditText mTitle, mSubTitle, mText, mTicket;

    public NotificationDialogView(Context context) {
        super(context);
        inflate(context, R.layout.notification_dialog_view, this);

        findViews();
    }

    private void findViews() {
        mTitle      = (EditText) findViewById(R.id.etTitle_NDV);
        mSubTitle   = (EditText) findViewById(R.id.etSubTitle_NDV);
        mText       = (EditText) findViewById(R.id.etText_NDV);
        mTicket     = (EditText) findViewById(R.id.etTicket_NDV);

    }

    public void setBuilder(NotificationCompat.Builder _builder) {
        mTitle.setText(_builder.mContentTitle);
        mSubTitle.setText(_builder.mSubText);
        mText.setText(_builder.mContentText);
        mTicket.setText(_builder.mNotification.tickerText);
    }

    public String getBuilderTitle()        {return mTitle.getText().toString();}
    public String getBuilderSubTitle()     {return mSubTitle.getText().toString();}
    public String getBuilderText()         {return mText.getText().toString();}
    public String getBuilderTicketText()   {return mTicket.getText().toString();}

    public void setBuilderTitle(String str)        {mTitle.setText(str);}
    public void setBuilderSubTitle(String str)     {mSubTitle.setText(str);}
    public void setBuilderText(String str)         {mText.setText(str);}
    public void setBuilderTicketText(String str)   {mTicket.setText(str);}
}
