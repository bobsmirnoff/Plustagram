package com.bobsmirnoff.plustagram20.Dialogs;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.MainActivity;
import com.bobsmirnoff.plustagram20.R;
import com.bobsmirnoff.plustagram20.SinglePersonActivity;

public class CommentDialog extends DialogFragment implements View.OnClickListener {

    EditText editText;
    Button okButton;
    Button cancelButton;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(SinglePersonActivity.OLD_NAME_KEY);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.comment_dialog, null);

        okButton = (Button) v.findViewById(R.id.yesDialogButton);
        okButton.setText("Seems legit");

        okButton = (Button) v.findViewById(R.id.cancelDialogButton);
        okButton.setText("Cancel!");

        v.findViewById(R.id.yesDialogButton).setOnClickListener(this);
        v.findViewById(R.id.cancelDialogButton).setOnClickListener(this);
        Log.d(MainActivity.TAG, name);
        ((TextView) v.findViewById(R.id.commentUsername)).setText(name);

        editText = (EditText) v.findViewById(R.id.commentField);
        editText.setHint("Why a plus?");
        editText.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesDialogButton:
                String comment = editText.getText().toString();
                ReturnDialogInfo activity = (ReturnDialogInfo) getActivity();
                activity.onFinishEditDialog(comment);
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