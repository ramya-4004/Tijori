package com.example.lockmyfile.Main;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import com.example.lockmyfile.Files.FileDetails;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FileLoader extends AsyncTaskLoader<HashMap<String, List<FileDetails>>> {

    private final static String IMAGE = "images";
    private final static String VIDEO = "videos";
    private final static String PDF = "pdf";

    public FileLoader(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public HashMap<String, List<FileDetails>> loadInBackground() {

        HashMap<String, List<FileDetails>> allFiles = new HashMap<>();
        String extension;

        //List with imageFile extensions
        List<String> imageExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

        //List with videoFile extensions
        List<String> videoExtensions = Arrays.asList("mp4", "mov", "wmv", "avi", "mkv", "mpeg-2");

        // pdf extension
        String pdfExtension = "pdf";
        allFiles.put(IMAGE, new ArrayList<FileDetails>());
        allFiles.put(VIDEO, new ArrayList<FileDetails>());
        allFiles.put(PDF, new ArrayList<FileDetails>());
        String path = getContext().getFilesDir().getPath();
        String[] files = getContext().fileList();

        for(int i=0; i<files.length; i++){
            extension = FilenameUtils.getExtension(files[i]);
            if(imageExtensions.contains(extension))
                allFiles.get(IMAGE).add(new FileDetails(path + "/" + files[i], 1));
            else if(videoExtensions.contains(extension))
                allFiles.get(VIDEO).add(new FileDetails(path + "/" + files[i], 2));
            else if(pdfExtension.equals(extension))
                allFiles.get(PDF).add(new FileDetails(path + "/" + files[i], 3));
            Log.i("Images", String.valueOf(allFiles.get(IMAGE).size()));
            Log.i("Pdfs", String.valueOf(allFiles.get(PDF).size()));
            Log.i("Videos", String.valueOf(allFiles.get(VIDEO).size()));


        }
        return allFiles;
    }
}
