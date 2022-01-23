package com.example.lockmyfile.Files;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import androidx.security.crypto.EncryptedFile;
import com.example.lockmyfile.Encryption.SharedPreferenceEncryption;

import java.io.*;

public class FileUtility {
    // method to get the actual path of file from file's URI

    private final static String LOG_TAG = FileUtility.class.getName();

    // get the file from the file's uri
    public static void getFile(Context context, Uri uri) {
        File dest = null;
        try {
            dest = new File(context.getFilesDir().getPath() + File.separatorChar + getFileName(context, uri));
            EncryptedFile destFile = SharedPreferenceEncryption.encryptCopiedFile(dest, context.getApplicationContext());
            InputStream ios = context.getContentResolver().openInputStream(uri);
            copyFile(ios, destFile);
            Log.i(LOG_TAG, "File copied Successfully");
            deleteFile(context, uri);
        } catch (FileNotFoundException e) {
            Log.i(LOG_TAG, "File Saving Unsuccessful");
        }
    }

    // get the file's name
    private static String getFileName(Context context, Uri uri) {
        Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();

        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    // copy file's content into destination file using inputStream
    private static void copyFile(InputStream ios, EncryptedFile destination) {

        try {
            OutputStream os = destination.openFileOutput();
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ios.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    // delete file from the original location
    private static void deleteFile(Context context, Uri uri) {
        try {
            DocumentsContract.deleteDocument(context.getContentResolver(), uri);
            Log.i(LOG_TAG, "File deleted successfully");
        } catch (FileNotFoundException e) {
            Log.i(LOG_TAG, "File not deleted");
        }

    }
}