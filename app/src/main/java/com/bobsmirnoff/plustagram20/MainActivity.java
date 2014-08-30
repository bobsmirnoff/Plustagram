package com.bobsmirnoff.plustagram20;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bobsmirnoff.plustagram20.Database.DBHelper;
import com.bobsmirnoff.plustagram20.Database.DBWorker;
import com.bobsmirnoff.plustagram20.Dialogs.NewEntryDialog;
import com.bobsmirnoff.plustagram20.Dialogs.ReturnDialogInfo;

public class MainActivity extends Activity implements ReturnDialogInfo, AdapterView.OnItemClickListener {

    public static final String ENTRY_ID_KEY = "id";
    public static final String ENTRY_ACTIVITY_NAME_KEY = "name";
    public static final String ENTRY_ACTIVITY_PLUSES_COUNT_KEY = "count";

    public static final String TAG = "MyActivity";
    private static final int REQUEST_CODE = 69;
    final String ATTRIBUTE_NAME = "text";
    final String ATTRIBUTE_PLUS = "pluses";

    ListView list;
    DBWorker db;
    NewEntryDialog dialog;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);

        list = (ListView) findViewById(R.id.lvMain);
        dialog = new NewEntryDialog();

        db = new DBWorker(this);
        db.open();

        cursor = db.getAllSorted();
        startManagingCursor(cursor);

        String from[] = new String[]{DBWorker.COLUMN_NAME, DBWorker.COLUMN_COUNT};
        int to[] = new int[]{R.id.item_name, R.id.item_pluses};

        MainListAdapter adapter = new MainListAdapter(this, R.layout.main_list_item, cursor, from, to);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        Button addButton = (Button) (findViewById(R.id.addFriendButton));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getFragmentManager(), "new_entry_dialog");
                dialog.setTargetFragment(dialog, REQUEST_CODE);
            }
        };

        addButton.setOnClickListener(listener);
    }

    @Override
    public void onFinishEditDialog(String input) {
        db.addRecord(input);
        cursor.requery();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SinglePersonActivity.class);
        SQLiteCursor sqlCursor = (SQLiteCursor) parent.getItemAtPosition(position);
        intent.putExtra(ENTRY_ID_KEY, sqlCursor.getInt(sqlCursor.getColumnIndex(DBHelper.COLUMN_ID)));
        db.close();

        startActivity(intent);
    }
}
