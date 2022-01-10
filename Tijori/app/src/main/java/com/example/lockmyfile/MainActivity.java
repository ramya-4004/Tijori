package com.example.lockmyfile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    // Floating Action Button to lock a file
    FloatingActionButton addFab;

    // fragment to check permission and browse through files
    FileChooserFragment fragment;

    // Button to view all files locked in the app
    Button showBtn;

    // boolean to know if the user has unlocked the app once
    public static boolean unlocked;

    /**
     *  Declarations to setup authentication to open the app
*/
    public static Executor executor;
    public static BiometricPrompt biometricPrompt;
    public static BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        ApplicationLifecycleObserver observer = new ApplicationLifecycleObserver();
        observer.registerLifecycle(getLifecycle());

         */

        setContentView(R.layout.activity_main);

        final FrameLayout frameLayout = findViewById(R.id.mainActivityLayout);

        unlocked = false;

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                unlocked = true;

                // make the activity visible only when authentication is successful
                frameLayout.setVisibility(FrameLayout.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login for Tijori App")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();



        addFab = findViewById(R.id.add_fab);
        addFab.setOnClickListener(addAFile);

        FragmentManager manager = this.getSupportFragmentManager();
        fragment = (FileChooserFragment) manager.findFragmentById(R.id.fragment_fileChooser);

        showBtn = findViewById(R.id.show_files_btn);
        showBtn.setOnClickListener(startShowFilesActivity);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (unlocked == false) {
            biometricPrompt.authenticate(promptInfo);
        }
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