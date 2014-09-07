package com.bobsmirnoff.plustagram20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NativeActivity extends Activity {

    private TextView appText;
    private TextView tapText;
    private TextView welcomeText;

    private int count = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            count++;
            switch (count) {
                case 1:
                    welcomeText.setVisibility(View.INVISIBLE);
                    appText.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun) {
            setContentView(R.layout.native_layout);
            ((Button) findViewById(R.id.addFriendButton)).setEnabled(false);
            appText = (TextView) findViewById(R.id.app);
            appText.setVisibility(View.INVISIBLE);
            tapText = (TextView) findViewById(R.id.tap);
            welcomeText = (TextView) findViewById(R.id.welcome);
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", false).commit();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

