package com.jj.honeypot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PresentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presents);
    }

    public void onClickPresentsFab(View view) {
        Log.d("test", "hello from fab");
    }
}
