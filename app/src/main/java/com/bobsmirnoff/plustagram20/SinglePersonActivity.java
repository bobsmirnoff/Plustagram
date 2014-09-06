package com.bobsmirnoff.plustagram20;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bobsmirnoff.plustagram20.Database.DBHelper;
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

    Cursor personalDataCursor;
    Cursor plusesCursor;
    private DBWorker dbw;

    private long entryId;
    private String entryName;
    private int entryCount;

    private int dialogAction;

    private TextView TVname;
    private TextView TVcount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_person_layout);

        dbw = new DBWorker(this);
        dbw.open();

        Intent intent = getIntent();
        entryId = intent.getIntExtra(MainActivity.ENTRY_ID_KEY, -1);

        personalDataCursor = dbw.getPersonById(entryId);

        if (personalDataCursor != null) {
            if (personalDataCursor.moveToFirst()) {
                do {
                    entryName = personalDataCursor.getString(personalDataCursor.getColumnIndex(DBHelper.PEOPLE_COLUMN_NAME));
                    entryCount = personalDataCursor.getInt(personalDataCursor.getColumnIndex(DBHelper.PEOPLE_COLUMN_COUNT));
                } while (personalDataCursor.moveToNext());
            }
            personalDataCursor.close();
        }


        Button editButton = (Button) findViewById(R.id.single_edit);
        Button deleteButton = (Button) findViewById(R.id.single_delete);

        plusesCursor = dbw.getPlussesForId(entryId);
        startManagingCursor(plusesCursor);
        ListView commentsList = (ListView) findViewById(R.id.commentsList);
        String from[] = new String[]{DBHelper.PLUSES_COLUMN_DATE, DBHelper.PLUSES_COLUMN_COMMENT};
        int to[] = new int[]{R.id.item_comment_date, R.id.item_comment_comment};
        CommentsListAdapter adapter = new CommentsListAdapter(this, R.layout.single_list_item, plusesCursor, from, to);
        commentsList.setAdapter(adapter);

        TVname = (TextView) findViewById(R.id.single_name);
        TVcount = (TextView) findViewById(R.id.single_pluses);

        TVcount.setOnClickListener(this);
        setTextToCircle(TVcount, entryCount);
        TVname.setText(entryName);

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
                dbw.renamePerson(entryId, input);
                TVname.setText(input);
                entryName = input;
                break;

            case ACTION_DELETE:
                if (input == RECORD_DELETED_KEY) {
                    dbw.deletePerson(entryId);
                    plusesCursor.close();
                    finish();
                }
                break;

            case ACTION_INCREMENT:
                dbw.plus(entryId, input);
                setTextToCircle(TVcount, ++entryCount);
                plusesCursor.requery();
                break;
        }
    }

    private void setTextToCircle(TextView tv, int count) {
        int leftPadding = 20;
        int rightPadding = 20;
        int length = String.valueOf(count).length();

        if (count == 0) {
            tv.setTextColor(getResources().getColor(R.color.dark_gray));
            tv.setBackgroundResource(R.drawable.pluses_text_faded);
            tv.setText("0");
            leftPadding = 46;
            rightPadding = 45;
            length = 1;
        } else {
            tv.setText("+" + Integer.toString(count));
            tv.setTextColor(getResources().getColor(R.color.main_orange));
            tv.setBackgroundResource(R.drawable.pluses_text);
        }

        LinearLayout.LayoutParams paddingLayoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        int padding = 28 * length - 19;
        tv.setPadding(valueInDp(leftPadding), valueInDp(padding), valueInDp(rightPadding), valueInDp(padding + 1));
        tv.setLayoutParams(paddingLayoutParams);
    }
}