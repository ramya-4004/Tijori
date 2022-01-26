package com.example.lockmyfile.ViewFiles;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lockmyfile.Main.MainActivity;
import com.example.lockmyfile.Encryption.SharedPreferenceEncryption;
import com.example.lockmyfile.R;
import com.github.barteksc.pdfviewer.PDFView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ViewPdfActivity extends AppCompatActivity {

    // decrypted pdf
    private File pdfFile;

    // view to show PDF file
    private static PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        pdfView = findViewById(R.id.idPDFView);

        pdfFile = SharedPreferenceEncryption.decryptVideoOrPdf(new File(getIntent().getStringExtra("Path")), getApplicationContext());

        new RetrivePDFfromUrl().execute(pdfFile.getPath());
    }

    // to not open the app without authentication when this activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        deleteFile();
        finishAffinity();
        finish();
    }

    // Take the user back to ShowAllFilesActivity when back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteFile();
        Intent intent = new Intent(ViewPdfActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
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

    private void deleteFile(){
        if(pdfFile.exists()){
            pdfFile.delete();
        }
    }
}