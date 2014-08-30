package com.bobsmirnoff.plustagram20.Dialogs;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.R;
import com.bobsmirnoff.plustagram20.SinglePersonActivity;

public class DeleteDialog extends DialogFragment implements View.OnClickListener {

    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(SinglePersonActivity.OLD_NAME_KEY);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.delete_dialog, null);
        ((TextView) v.findViewById(R.id.username)).setText(name);
        v.findViewById(R.id.yesDialogButton).setOnClickListener(this);
        v.findViewById(R.id.cancelDialogButton).setOnClickListener(this);
        return v;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesDialogButton:
                ReturnDialogInfo activity = (ReturnDialogInfo) getActivity();
                activity.onFinishEditDialog(SinglePersonActivity.RECORD_DELETED_KEY);
            case R.id.cancelDialogButton:
                this.dismiss();
        }
    }

}
