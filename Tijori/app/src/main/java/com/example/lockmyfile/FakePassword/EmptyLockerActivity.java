package com.example.lockmyfile.FakePassword;

import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lockmyfile.R;
import com.example.lockmyfile.Utilities.SwipeGestureListener;

public class EmptyLockerActivity extends AppCompatActivity {

    SetPasswordDialog setPasswordDialog;

    // timer that runs in background to show as if application has crashed
    private Handler onlineTimeHandler = new Handler();
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private int secs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_locker);
        setPasswordDialog = new SetPasswordDialog();

        findViewById(R.id.emptyFrameLayout).setOnTouchListener(new SwipeGestureListener(this));

        setPasswordDialog.show(getSupportFragmentManager(), "Login Authentication");

        startTime = SystemClock.uptimeMillis();

        onlineTimeHandler.post(updateTimerThread);

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);

            // after 15 seconds close the app if no gesture detected
            if (secs >= 10) {
                finish();
            }

            onlineTimeHandler.postDelayed(this, 1 * 1000);

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}