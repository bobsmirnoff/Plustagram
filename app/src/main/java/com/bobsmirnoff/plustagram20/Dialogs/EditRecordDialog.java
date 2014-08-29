package com.bobsmirnoff.plustagram20.Dialogs;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.R;
import com.bobsmirnoff.plustagram20.SinglePersonActivity;

public class EditRecordDialog extends DialogFragment implements View.OnClickListener, TextWatcher {

    EditText editText;
    Button okButton;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(SinglePersonActivity.OLD_NAME_KEY);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.entry_dialog, null);

        okButton = (Button) v.findViewById(R.id.addDialogButton);
        okButton.setText("Seems legit");

        v.findViewById(R.id.addDialogButton).setOnClickListener(this);
        v.findViewById(R.id.cancelDialogButton).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.dialogTitle)).setText("Edit name");

        editText = (EditText) v.findViewById(R.id.newEntryDialogField);
        editText.setText(name);
        editText.setHint("New fancy name");
        editText.requestFocus();
        editText.addTextChangedListener(this);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addDialogButton:
                name = editText.getText().toString();
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
    public void onStart() {
        editText.setText(name);
        editText.setSelection(editText.getText().length());
        super.onStart();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) okButton.setEnabled(false);
        else if (s.length() == 1) okButton.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
