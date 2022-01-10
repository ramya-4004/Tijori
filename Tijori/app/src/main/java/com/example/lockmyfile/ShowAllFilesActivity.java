package com.example.lockmyfile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowAllFilesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<FileDetails>> {

    private final static String LOG_TAG = ShowAllFilesActivity.class.getName();

    ListView filesListView;

    FileDetailsAdapter adapter;

    TextView emptyTextView;

    private static int loaderId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        ApplicationLifecycleObserver observer = new ApplicationLifecycleObserver();
        observer.registerLifecycle(getLifecycle());

         */

        setContentView(R.layout.activity_show_all_files);

        filesListView = findViewById(R.id.files_list_view);

        emptyTextView = findViewById(R.id.empty_text_view);

        adapter = new FileDetailsAdapter(this, 0, new ArrayList<FileDetails>());
        filesListView.setAdapter(adapter);

        LoaderManager.getInstance(ShowAllFilesActivity.this).initLoader(loaderId, null, ShowAllFilesActivity.this);
        loaderId++;

    }


    /**
    private ArrayList<FileDetails> getAllFilesWithoutPath(){
        String[] files = getApplicationContext().fileList();
        ArrayList<FileDetails> fileList = new ArrayList<>();

        for(int i=0; i<files.length; i++){
            fileList.add(new FileDetails(files[i]));
        }
        return fileList;
    }
    */

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
        adapter.clear();

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else{
            emptyTextView.setText(R.string.no_result_found);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<FileDetails>> loader) {
        adapter.clear();
    }
}