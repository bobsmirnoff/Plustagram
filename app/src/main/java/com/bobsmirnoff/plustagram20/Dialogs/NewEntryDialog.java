package com.bobsmirnoff.plustagram20.Dialogs;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.R;

public class NewEntryDialog extends DialogFragment implements View.OnClickListener {

    EditText editText;
    Button okButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entry_dialog, null);

        okButton = (Button) v.findViewById(R.id.addDialogButton);
        okButton.setText("Ok, you can be on the list");
        v.findViewById(R.id.addDialogButton).setOnClickListener(this);
        v.findViewById(R.id.cancelDialogButton).setOnClickListener(this);

        ((TextView) v.findViewById(R.id.dialogTitle)).setText("New entry");

        editText = (EditText) v.findViewById(R.id.newEntryDialogField);
        editText.setHint("A cool name here");
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addDialogButton:
                ReturnDialogInfo activity = (ReturnDialogInfo) getActivity();
                activity.onFinishEditDialog(editText.getText().toString());
            case R.id.cancelDialogButton:
                this.dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        editText.setText("");
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
