package com.bobsmirnoff.plustagram20;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.Database.DBWorker;
import com.bobsmirnoff.plustagram20.Dialogs.CommentDialog;
import com.bobsmirnoff.plustagram20.Dialogs.DeleteDialog;
import com.bobsmirnoff.plustagram20.Dialogs.EditRecordDialog;
import com.bobsmirnoff.plustagram20.Dialogs.ReturnDialogInfo;

public class SinglePersonActivity extends Activity implements View.OnClickListener, ReturnDialogInfo {

    public static final int REQUEST_CODE_EDIT = 10;
    public static final int REQUEST_CODE_DELETE = 11;

    public static final String OLD_NAME_KEY = "old_name";
    public static final String RECORD_DELETED_KEY = "record deleted";
    private static final int REQUEST_CODE_COMMENT = 12;

    private static final int ACTION_RENAME = 1;
    private static final int ACTION_DELETE = 2;
    private static final int ACTION_INCREMENT = 3;

    Cursor cursor;
    private DBWorker db;

    private long entryId;
    private String entryName;
    private String entryCount;

    private int dialogAction;

    private TextView TVname;
    private TextView TVcount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_person_layout);

        db = new DBWorker(this);
        db.open();

        Intent intent = getIntent();
        entryId = intent.getIntExtra(MainActivity.ENTRY_ID_KEY, -1);

        cursor = db.getById(entryId);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    entryName = cursor.getString(cursor.getColumnIndex(DBWorker.COLUMN_NAME));
                    entryCount = cursor.getString(cursor.getColumnIndex(DBWorker.COLUMN_COUNT));
                    Log.d(MainActivity.TAG, String.valueOf(entryId) + entryName + String.valueOf(entryCount));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

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
    }

    private int valueInDp(int sizeInPx) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (sizeInPx * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {

        Bundle args = new Bundle();
        args.putString(OLD_NAME_KEY, entryName);

        switch (v.getId()) {
            case R.id.single_edit:
                EditRecordDialog editDialog = new EditRecordDialog();

                editDialog.setArguments(args);
                editDialog.show(getFragmentManager(), "edit_entry_dialog");
                editDialog.setTargetFragment(editDialog, REQUEST_CODE_EDIT);
                dialogAction = ACTION_RENAME;
                break;

            case R.id.single_delete:
                DeleteDialog deleteDialog = new DeleteDialog();

                deleteDialog.setArguments(args);
                deleteDialog.show(getFragmentManager(), "delete_entry_dialog");
                deleteDialog.setTargetFragment(deleteDialog, REQUEST_CODE_DELETE);
                dialogAction = ACTION_DELETE;

                break;

            case R.id.single_pluses:
                CommentDialog commentDialog = new CommentDialog();

                commentDialog.setArguments(args);
                commentDialog.show(getFragmentManager(), "comment_plus_dialog");
                commentDialog.setTargetFragment(commentDialog, REQUEST_CODE_COMMENT);
                dialogAction = ACTION_INCREMENT;
                break;
        }
    }

    @Override
    public void onFinishEditDialog(String input) {
        switch (dialogAction) {
            case ACTION_RENAME:
                db.rename(entryId, input);
                TVname.setText(input);
                entryName = input;
                break;

            case ACTION_DELETE:
                if (input == RECORD_DELETED_KEY) {
                    db.delete(entryId);
                    finish();
                }
                break;

            case ACTION_INCREMENT:
                //TODO if commented
                break;
        }
    }
}