package com.example.lockmyfile.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;
import com.example.lockmyfile.Files.FileChooserFragment;
import com.example.lockmyfile.Utilities.ApplicationLifecycleObserver;
import com.example.lockmyfile.Files.FileDetails;
import com.example.lockmyfile.R;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HashMap<String, List<FileDetails>>>{

    private final static int PERMISSION_REQUEST_CODE = 200;

    LinearLayout linearLayout;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    HashMap<String, List<FileDetails>> allFiles;

    private final static String IMAGE = "images";
    private final static String VIDEO = "videos";
    private final static String PDF = "pdf";

    private static int loaderId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setTitle(R.string.app_name);

        executor = ContextCompat.getMainExecutor(this);
        setupBiometric();

        linearLayout = findViewById(R.id.mainActivityLayout);

        allFiles = new HashMap<>();

        final ViewPager viewPager = findViewById(R.id.viewpager2);
        final TabLayout tabLayout = findViewById(R.id.tabLayout2);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_image));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_video));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pdf));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LoaderManager.getInstance(MainActivity.this).initLoader(loaderId, null, MainActivity.this);
        loaderId++;

        final FragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(fragmentPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupBiometric(){
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                ApplicationLifecycleObserver.stillInApp = true;
                linearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login for Tijori App")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL | BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build();

    }

    // function to send list of image files
    public List<FileDetails> getImages(){
        return allFiles.get(IMAGE);
    }

    // function to send list of video files
    public List<FileDetails> getVideos(){
        return allFiles.get(VIDEO);
    }

    // function to send list of pdf files
    public List<FileDetails> getPdf(){
        return allFiles.get(PDF);
    }

    @NonNull
    @NotNull
    @Override
    public Loader<HashMap<String, List<FileDetails>>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        return new FileLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<HashMap<String, List<FileDetails>>> loader, HashMap<String, List<FileDetails>> data) {
        if(data != null){
            allFiles = data;
        }
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<HashMap<String, List<FileDetails>>> loader) {
        allFiles = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ApplicationLifecycleObserver.stillInApp == false){
            biometricPrompt.authenticate(promptInfo);
        } else{
            linearLayout.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ApplicationLifecycleObserver.stillInApp = false;
        finishAffinity();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!ApplicationLifecycleObserver.stillInApp) {
            finishAffinity();
            finish();
        }
    }

}