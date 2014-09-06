package com.bobsmirnoff.plustagram20;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommentsListAdapter extends SimpleCursorAdapter {

    public CommentsListAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }


    @Override
    public void setViewText(TextView v, String text) {
        if (v.getId() == R.id.item_comment_comment) {
            if (text.equals("")) {
                super.setViewText(v, "no comment provided");
                v.setTypeface(null, Typeface.ITALIC);
                v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
                v.setTextColor(R.color.light_gray);
            } else {
                v.setTypeface(null, Typeface.NORMAL);
                v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                v.setTextColor(Color.BLACK);
                super.setViewText(v, text);
            }
        } else if (v.getId() == R.id.item_comment_date) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            super.setViewText(v, text);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 0);
            if (text.equals(format.format(cal.getTime()))) {
                super.setViewText(v, "just today");
            }

            cal.add(Calendar.DATE, -1);
            if (text.equals(format.format(cal.getTime()))) {
                super.setViewText(v, "yesterday");
            }
        }
    }
}
