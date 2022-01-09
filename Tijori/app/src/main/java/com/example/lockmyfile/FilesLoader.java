package com.example.lockmyfile;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class FilesLoader extends AsyncTaskLoader<List<FileDetails>> {

    public FilesLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<FileDetails> loadInBackground() {
        String[] files = getContext().fileList();
        ArrayList<FileDetails> fileList = new ArrayList<>();

        for(int i=0; i<files.length; i++){
            Log.i("LOADER", "SUCCESSFUL");
            fileList.add(new FileDetails(files[i]));
        }
        return fileList;
    }
}