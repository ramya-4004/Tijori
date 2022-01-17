package com.example.lockmyfile;

import android.content.Intent;
import android.view.*;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Floating Action Button to lock a file
    FloatingActionButton addFab;

    // fragment to check permission and browse through files
    FileChooserFragment fragment;

    // Button to view all files locked in the app
    Button showBtn;

    // boolean to check whether app is unlocked or not
    public static boolean unlocked;

    FrameLayout frameLayout;

    // ImageView to start Web browser
    ImageView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // setting up biometric authentication
        BiometricAuthentication.setupBiometric(this);

        setContentView(R.layout.activity_main);

        unlocked = false;

        frameLayout = findViewById(R.id.mainActivityLayout);
        webView = findViewById(R.id.openWebImageView);

        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BrowserActivity.class));
            }
        });

        addFab = findViewById(R.id.add_fab);
        addFab.setOnClickListener(addAFile);

        FragmentManager manager = this.getSupportFragmentManager();
        fragment = (FileChooserFragment) manager.findFragmentById(R.id.fragment_fileChooser);

        showBtn = findViewById(R.id.show_files_btn);
        showBtn.setOnClickListener(startShowFilesActivity);

        SetPasswordDialog setPasswordDialog = new SetPasswordDialog(frameLayout, 1);
        setPasswordDialog.show(getSupportFragmentManager(), "Set Password");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.set_fake_pwd:
                SetPasswordDialog setFakePasswordDialog = new SetPasswordDialog(frameLayout, 2);
                setFakePasswordDialog.show(getSupportFragmentManager(), "Set Fake Password");
                break;

            case R.id.change_pwd:
                BiometricAuthentication.authenticateUser();
                SetPasswordDialog changePasswordDialog = new SetPasswordDialog(frameLayout, 3);
                changePasswordDialog.show(getSupportFragmentManager(), "Change Password");
                break;
        }
        return true;
    }

    // Setting on click listener for the button
    View.OnClickListener addAFile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean status = fragment.getFragmentWorking();

            if(!status){
                Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // listener to switch to ShowAllFilesActivity to show all the files present inside the app
    View.OnClickListener startShowFilesActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BiometricAuthentication.authenticateUser();
            Intent intent = new Intent(MainActivity.this, ShowAllFilesActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}