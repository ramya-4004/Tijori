package com.example.lockmyfile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Floating Action Button to lock a file
    FloatingActionButton addFab;

    // fragment to check permission and browse through files
    FileChooserFragment fragment;

    // Button to view all files locked in the app
    Button showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFab = findViewById(R.id.add_fab);
        addFab.setOnClickListener(addAFile);

        FragmentManager manager = this.getSupportFragmentManager();
        fragment = (FileChooserFragment) manager.findFragmentById(R.id.fragment_fileChooser);

        showBtn = findViewById(R.id.show_files_btn);
        showBtn.setOnClickListener(startShowFilesActivity);
    }

    // Setting on click listener for the button
    View.OnClickListener addAFile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkFragmentWorkingStatus();
        }
    };

    private void checkFragmentWorkingStatus(){
        boolean status = this.fragment.getFragmentWorking();

        if(status == true){
        }
        else{
            Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    // onclicklistener to switch to ShowAllFilesActivity to show all the files present inside the app
    View.OnClickListener startShowFilesActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ShowAllFilesActivity.class);
            startActivity(intent);
        }
    };

}