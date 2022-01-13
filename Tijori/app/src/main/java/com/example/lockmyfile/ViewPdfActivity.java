package com.example.lockmyfile;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ViewPdfActivity extends AppCompatActivity {

    // String to hold the path of pdf file
    private String pdfFile;

    // view to show PDF file
    private static PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        pdfView = findViewById(R.id.idPDFView);

        pdfFile = getIntent().getStringExtra("pdfPath");

        new RetrivePDFfromUrl().execute(pdfFile);
    }

    // to not open the app without authentication when this activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    // Take the user back to ShowAllFilesActivity when back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent showFilesIntent = new Intent(ViewPdfActivity.this, ShowAllFilesActivity.class);
        startActivity(showFilesIntent);
    }

    // create an async task class for loading pdf file from URL.
    private static class RetrivePDFfromUrl extends AsyncTask<String, Void, FileInputStream> {

        @Override
        protected FileInputStream doInBackground(String... strings) {

            String path = strings[0];
            FileInputStream inputStream = null;
            try{
                File pdf = new File(path);
                inputStream = new FileInputStream(pdf);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(FileInputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }
}