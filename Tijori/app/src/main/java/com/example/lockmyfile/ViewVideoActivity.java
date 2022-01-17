package com.example.lockmyfile;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ViewVideoActivity extends AppCompatActivity{

    // Variable to hold the VideoView widget in xml
    VideoView videoView;

    // String variable to hold the path of the video to be played
    String path = "";

    // LOG_TAG variable to hold the class name, used to send log messages
    private static final String LOG_TAG = ViewVideoActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        path = getIntent().getStringExtra("videoPath");

        videoView = findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));

        setupVideo();

    }

    // function to setup the video path and start playing the video
    private void setupVideo(){
        videoView.setVideoPath(path);
        videoView.start();

    }


    // to not open the app without authentication when this activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    // Take the user back to ShowAllFilesActivity when back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent showFilesIntent = new Intent(ViewVideoActivity.this, ShowAllFilesActivity.class);
        startActivity(showFilesIntent);
    }
}