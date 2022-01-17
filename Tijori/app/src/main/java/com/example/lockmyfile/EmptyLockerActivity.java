package com.example.lockmyfile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyLockerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_locker);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}