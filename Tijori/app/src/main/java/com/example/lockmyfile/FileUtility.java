package com.example.lockmyfile;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;
import androidx.loader.content.CursorLoader;

import java.io.*;

public class FileUtility {
    // method to get the actual path of file from file's URI

    private final static String LOG_TAG = FileUtility.class.getName();

    // get the file from the file's uri
    public static void getFile(Context context, Uri uri){
        File dest = null;
        try {
            dest = new File(context.getFilesDir().getPath() + File.separatorChar + getFileName(context, uri));
            InputStream ios = context.getContentResolver().openInputStream(uri);
            copyFile(ios, dest);
            Log.i(LOG_TAG, "File copied Successfully");
            deleteFile(context, uri);
        } catch (FileNotFoundException e) {
            Log.i(LOG_TAG, "File Saving Unsuccessful");
        }
    }

    // get the file's name
    private static String getFileName(Context context, Uri uri){
        Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    // copy file's content into destination file using inputStream
    private static void copyFile(InputStream ios, File destination){

        try{
            OutputStream os = new FileOutputStream(destination);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ios.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    // delete file from the original location
    private static void deleteFile(Context context, Uri uri){
        try {
            DocumentsContract.deleteDocument(context.getContentResolver(), uri);
            Log.i(LOG_TAG, "File deleted successfully");
        } catch (FileNotFoundException e) {
            Log.i(LOG_TAG, "File not deleted");
        }

    }

    /** Function to get actual path of file

     public static String getActualPath(Context context, Uri fileUri) {
     String realPath;
     // SDK > API19
     realPath = FileUtility.getRealPathFromURI_API19(context, fileUri);
     return realPath;
     }

     public static String getRealPathFromURI_API19(final Context context, final Uri uri) {
     // DocumentProvider
     if (DocumentsContract.isDocumentUri(context, uri)) {
     // ExternalStorageProvider
     if (isExternalStorageDocument(uri)) {
     final String docId = DocumentsContract.getDocumentId(uri);
     final String[] split = docId.split(":");
     final String type = split[0];

     if ("primary".equalsIgnoreCase(type)) {
     return context.getExternalFilesDir(null) + "/" + split[1];
     }

     // TODO handle non-primary volumes
     }
     // DownloadsProvider
     else if (isDownloadsDocument(uri)) {

     final String id = DocumentsContract.getDocumentId(uri);
     final Uri contentUri = ContentUris.withAppendedId(
     Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


     return getDataColumn(context, contentUri, null, null);
     }
     // MediaProvider
     else if (isMediaDocument(uri)) {
     final String docId = DocumentsContract.getDocumentId(uri);
     final String[] split = docId.split(":");
     final String type = split[0];

     Uri contentUri = contentUri = MediaStore.Files.getContentUri("external");

     final String selection = "_id=?";
     final String[] selectionArgs = new String[]{
     split[1]
     };

     return getDataColumn(context, contentUri, selection, selectionArgs);
     }
     }
     // MediaStore (and general)
     else if ("content".equalsIgnoreCase(uri.getScheme())) {

     // Return the remote address
     if (isGooglePhotosUri(uri))
     return uri.getLastPathSegment();

     return getDataColumn(context, uri, null, null);
     }
     // File
     else if ("file".equalsIgnoreCase(uri.getScheme())) {
     return uri.getPath();
     }

     return null;
     }

     public static String getDataColumn(Context context, Uri uri, String selection,
     String[] selectionArgs) {

     String columnData = null;

     Cursor cursor = null;
     final String column = "_data";
     final String[] projection = {
     column
     };

     try {
     cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
     if (cursor != null && cursor.moveToFirst()) {
     final int index = cursor.getColumnIndexOrThrow(column);
     columnData = cursor.getString(index);
     }
     } catch (Exception e){
     e.printStackTrace();
     } finally {
     if (cursor != null)
     cursor.close();
     }
     return columnData;
     }

     public static boolean isExternalStorageDocument(Uri uri) {
     return "com.android.externalstorage.documents".equals(uri.getAuthority());
     }

     public static boolean isDownloadsDocument(Uri uri) {
     return "com.android.providers.downloads.documents".equals(uri.getAuthority());
     }

     public static boolean isMediaDocument(Uri uri) {
     return "com.android.providers.media.documents".equals(uri.getAuthority());
     }

     public static boolean isGooglePhotosUri(Uri uri) {
     return "com.google.android.apps.photos.content".equals(uri.getAuthority());
     }


     */

}