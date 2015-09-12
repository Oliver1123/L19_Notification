package com.example.oliver.l19_notification.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.oliver.l19_notification.Constants;
import com.example.oliver.l19_notification.CustomView.NotificationDialogView;

/**
 * Created by oliver on 12.09.15.
 */
public class NotificationDialog extends DialogFragment implements DialogInterface.OnClickListener{

    NotificationDialogView view;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = new NotificationDialogView(getActivity());

        Bundle args = getArguments();
        if ( args != null) {
            String title        = args.getString(Constants.BUILDER_ARG_TITLE);
            String subtitle     = args.getString(Constants.BUILDER_ARG_SUBTITLE);
            String message      = args.getString(Constants.BUILDER_ARG_MESSAGE);
            String tickerText   = args.getString(Constants.BUILDER_ARG_TICKER_TEXT);

            view.setBuilderTitle(title);
            view.setBuilderSubTitle(subtitle);
            view.setBuilderText(message);
            view.setBuilderTicketText(tickerText);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (view.getBuilderTitle().isEmpty() &&
            view.getBuilderSubTitle().isEmpty() &&
            view.getBuilderText().isEmpty() &&
            view.getBuilderTicketText().isEmpty()) {
            return;
        }
        Intent result = new Intent();
        result.putExtra(Constants.BUILDER_ARG_TITLE, view.getBuilderTitle());
        result.putExtra(Constants.BUILDER_ARG_SUBTITLE, view.getBuilderSubTitle());
        result.putExtra(Constants.BUILDER_ARG_MESSAGE, view.getBuilderText());
        result.putExtra(Constants.BUILDER_ARG_TICKER_TEXT, view.getBuilderTicketText());

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
    }
}
