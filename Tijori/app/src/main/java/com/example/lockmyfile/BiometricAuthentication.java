package com.example.lockmyfile;

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BiometricAuthentication {
    // boolean to know if the user has unlocked the app once
    public static boolean unlocked;

    /**
     *  Declarations to setup authentication to open the app
     */
    public static Executor executor;
    public static BiometricPrompt biometricPrompt;
    public static BiometricPrompt.PromptInfo promptInfo;

    // function to setup biometric prompt
    public static void setupBiometric(final FragmentActivity activity){
        unlocked = false;

        executor = ContextCompat.getMainExecutor(activity.getApplicationContext());
        biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                activity.finish();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                unlocked = true;

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login for Tijori App")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

    }

    public static void authenticateUser(){
        biometricPrompt.authenticate(promptInfo);
    }


}
