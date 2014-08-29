package com.bobsmirnoff.plustagram20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.Dialogs.EditRecordDialog;
import com.bobsmirnoff.plustagram20.Dialogs.ReturnDialogInfo;

import java.util.ArrayList;
import java.util.Vector;

public class SinglePersonActivity extends Activity implements View.OnClickListener, ReturnDialogInfo {

    public static final int REQUEST_CODE_EDIT = 69;
    public static final String OLD_NAME_KEY = "old_name";

    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_RENAME = "rename";
    private static final String ACTION_INCREMENT = "increment";

    ArrayList<Vector> actions;

    private String entryName;
    private String entryCount;
    private int id;


    private EditRecordDialog dialog;
    private TextView TVname;
    private TextView TVcount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_person_layout);

        Intent intent = getIntent();
        id = intent.getIntExtra(MainActivity.ENTRY_ID_KEY, -1);
        entryName = intent.getStringExtra(MainActivity.ENTRY_ACTIVITY_NAME_KEY);
        entryCount = intent.getStringExtra(MainActivity.ENTRY_ACTIVITY_PLUSES_COUNT_KEY);

        actions = new ArrayList<Vector>();

        Button editButton = (Button) findViewById(R.id.single_edit);
        Button deleteButton = (Button) findViewById(R.id.single_delete);

        TVname = (TextView) findViewById(R.id.single_name);
        TVcount = (TextView) findViewById(R.id.single_pluses);

        TVcount.setOnClickListener(this);

        TVname.setText(entryName);
        TVcount.setText(entryCount);

        int leftPadding = 20;
        int rightPadding = 20;
        int length = TVcount.length();

        if (Integer.valueOf(entryCount) == 0) {
            TVcount.setTextColor(R.color.dark_gray);
            TVcount.setBackgroundResource(R.drawable.pluses_text_faded);
            TVcount.setText("0");
            leftPadding = 46;
            rightPadding = 45;
            length = 1;
        } else TVcount.setText("+" + entryCount);

        LinearLayout.LayoutParams paddingLayoutParams = (LinearLayout.LayoutParams) TVcount.getLayoutParams();

        int padding = 28 * length - 20;
        TVcount.setPadding(valueInDp(leftPadding), valueInDp(padding) - 1, valueInDp(rightPadding), valueInDp(padding));

        TVcount.setLayoutParams(paddingLayoutParams);

        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        dialog = new EditRecordDialog();
    }

    private int valueInDp(int sizeInPx) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (sizeInPx * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        switch (v.getId()) {
            case R.id.single_edit:

                args.putString(OLD_NAME_KEY, entryName);
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "edit_entry_dialog");
                dialog.setTargetFragment(dialog, REQUEST_CODE_EDIT);

                break;

            case R.id.single_delete:
                //TODO delete dialog

                break;

            case R.id.single_pluses:
                //TODO comment dialog
                break;
        }
    }

    @Override
    public void onFinishEditDialog(String input) {
        entryName = input;
        TVname.setText(input);
    }
}