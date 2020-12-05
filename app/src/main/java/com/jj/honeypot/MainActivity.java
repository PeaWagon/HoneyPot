package com.jj.honeypot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickHoneyButton(View view) {
        Intent honeyActivityIntent = new Intent(this, HoneyActivity.class);
        startActivity(honeyActivityIntent);
    }

    public void onClickPresentsButton(View view) {
        Intent presentsActivityIntent = new Intent(this, PresentsActivity.class);
        startActivity(presentsActivityIntent);
    }

    public void onClickRemindersButton(View view) {
        Intent remindersActivityIntent = new Intent(this, RemindersActivity.class);
        startActivity(remindersActivityIntent);
    }
}
