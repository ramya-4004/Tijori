package com.example.lockmyfile.FakePassword;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lockmyfile.Main.MainActivity;
import com.example.lockmyfile.R;
import com.example.lockmyfile.Utilities.SwipeGestureListener;

public class EmptyLockerActivity extends AppCompatActivity {

    SetPasswordDialog setPasswordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_locker);
        setPasswordDialog = new SetPasswordDialog();

        findViewById(R.id.emptyFrameLayout).setOnTouchListener(new SwipeGestureListener(this));

        setPasswordDialog.show(getSupportFragmentManager(), "Login Authentication");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
        finish();
    }
}