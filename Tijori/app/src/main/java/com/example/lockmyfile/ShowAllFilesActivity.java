package com.example.lockmyfile;

import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ShowAllFilesActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<FileDetails>> {

    private final static String LOG_TAG = ShowAllFilesActivity.class.getName();

    RecyclerView recyclerView;

    List<FileDetails> fileDetailsList;

    RecyclerViewAdapter adapter;

    TextView emptyTextView;

    private static int loaderId = 0;

    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_show_all_files);

        frameLayout = findViewById(R.id.files_layout);

        recyclerView = findViewById(R.id.recycler_view);

        emptyTextView = findViewById(R.id.empty_text_view);

        fileDetailsList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(this, 4);

        adapter = new RecyclerViewAdapter(this, fileDetailsList);
        //defining recyclerView and setting the adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);



        LoaderManager.getInstance(ShowAllFilesActivity.this).initLoader(loaderId, null, ShowAllFilesActivity.this);
        loaderId++;

    }

    @Override
    protected void onStart() {
        super.onStart();
        BiometricAuthentication.setupBiometric(this);
        frameLayout.setVisibility(View.VISIBLE);
    }


    // to not open the app without authentication when this activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @NonNull
    @Override
    public Loader<List<FileDetails>> onCreateLoader(int id, @Nullable Bundle args) {
        return new FilesLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<FileDetails>> loader, List<FileDetails> data) {

        if (data != null && !data.isEmpty()) {
            adapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<FileDetails>> loader) {
        adapter.setData(new ArrayList<FileDetails>());
    }
}