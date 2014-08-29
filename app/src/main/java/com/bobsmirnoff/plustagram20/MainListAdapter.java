package com.bobsmirnoff.plustagram20;

import android.content.Context;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainListAdapter extends SimpleCursorAdapter {

    private final Context adapterContext;

    public MainListAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        adapterContext = context;
    }


    @Override
    public void setViewText(TextView v, String text) {

        if (v.getId() == R.id.item_pluses) {

            int length = 2;
            int leftPadding = 9;
            int rightPadding = 9;

            if (Integer.valueOf(text) == 0) {
                leftPadding = 17;
                rightPadding = 16;
                super.setViewText(v, text);
                v.setTextColor(R.color.dark_gray);
                v.setBackgroundResource(R.drawable.pluses_text_faded);
            } else {
                super.setViewText(v, "+" + text);
                length = v.length();
            }

            ViewGroup.LayoutParams paddingLayoutParams = v.getLayoutParams();
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            int padding = 7 * length - 8;
            int margin = (-1) * 6 * length + 35;
            v.setPadding(valueInDp(leftPadding), valueInDp(padding), valueInDp(rightPadding), valueInDp(padding));
            mlp.setMargins(0, 0, valueInDp(margin), 0);

            v.setLayoutParams(paddingLayoutParams);

        } else super.setViewText(v, text);
    }

    private int valueInDp(int sizeInPx) {
        float scale = adapterContext.getResources().getDisplayMetrics().density;
        return (int) (sizeInPx * scale + 0.5f);
    }
}
