package com.example.lockmyfile.ViewFiles;

import android.content.Intent;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lockmyfile.Main.MainActivity;
import com.example.lockmyfile.Encryption.SharedPreferenceEncryption;
import com.example.lockmyfile.R;

import java.io.File;

public class ViewVideoActivity extends AppCompatActivity{

    // Variable to hold the VideoView widget in xml
    VideoView videoView;

    // decrypted file
    File videoFile;

    // LOG_TAG variable to hold the class name, used to send log messages
    private static final String LOG_TAG = ViewVideoActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        videoView = findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));

        videoFile = SharedPreferenceEncryption.decryptVideoOrPdf(new File(getIntent().getStringExtra("Path")), getApplicationContext());

        setupVideo();

    }

    // function to setup the video path and start playing the video
    private void setupVideo(){
        videoView.setVideoPath(videoFile.getPath());
        videoView.start();

    }


    // to not open the app without authentication when this activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        deleteFile();
        finish();
    }

    // Take the user back to ShowAllFilesActivity when back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteFile();

        Intent showFilesIntent = new Intent(ViewVideoActivity.this, MainActivity.class);
        startActivity(showFilesIntent);
    }

    private void deleteFile(){
        if(videoFile.exists()){
            videoView.pause();
            videoFile.delete();
        }
    }
}