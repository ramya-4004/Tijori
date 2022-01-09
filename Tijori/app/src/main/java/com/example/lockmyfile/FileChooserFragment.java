package com.example.lockmyfile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
    This fragment first checks for access to external storage permission
    and lets user browse through the files to select one to be locked.
 */
public class FileChooserFragment extends Fragment {

    // declaration of request code for read and write access
    private final static int PERMISSION_REQUEST_CODE = 30;

    // declaration of request code for read and write access
    private final static int FILE_REQUEST_CODE = 32;

    // declaring permissions
    private static String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // activity launcher to take the user to settings to give access to the app
    private static ActivityResultLauncher<Intent> activityPermissionResultLauncher;

    // activity launcher to let user browse through files
    private static ActivityResultLauncher<Intent> activityBrowsingResultLauncher;

    // classname for debugging through Log
    private static final String LOG_TAG = FileChooserFragment.class.getName();

    // browse button to select files
    Button browseBtn;

    // check status of working of fragment
    private boolean fragmentWorking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_file_chooser, container, false);
        fragmentWorking = false;

        // takes the user to permissions settings to let them give permission; applicable in API level >= 30
        activityPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        // printing appropriate Toast messages
                        if(Environment.isExternalStorageManager()){
                            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        activityBrowsingResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Uri uri = null;

                    if(result != null){
                        uri = result.getData().getData();
                        FileUtility.getFile(getContext(), uri);
                    }
                }
            }
        });

        // return this view to MainActivity
        return rootView;
    }

    // function to check whether the app has access to external storage
    boolean checkPermission(){

        // for API Level >= 30
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            // return true when permission for external storage is granted
            return Environment.isExternalStorageManager();
        }
        // for API Level < 30
        else{

            // checking read permission
            int readCheck = ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

            // checking write permission
            int writeCheck = ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            // return true if both the permissions are granted
            return (readCheck == PackageManager.PERMISSION_GRANTED) && (writeCheck == PackageManager.PERMISSION_GRANTED);
        }
    }

    // function to request for access to external storage
    void requestPermission(){

        // for API level >= 30
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            Intent requestIntent = null;
            try{
                requestIntent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                requestIntent.addCategory("android.intent.category.DEFAULT");
                requestIntent.setData(Uri.parse(String.format("package:%s", new Object[]{this.getContext().getPackageName()})));
            }
            catch (Exception e){
                requestIntent = new Intent();
                requestIntent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            }
            finally{
                activityPermissionResultLauncher.launch(requestIntent);
            }
        }
        // for API level < 30
        else{
            ActivityCompat.requestPermissions(this.getActivity(), permissions, PERMISSION_REQUEST_CODE);
        }
    }

    // overriding method to request permission to check permission results; applicable in API level < 30
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0){
                // obtaining results of READ permission request
                boolean readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                // obtaining results of WRITE permission request
                boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                // Toast messages to know appropriate permission results.
                if(readPermission && writePermission){
                    Toast.makeText(this.getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this.getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this.getContext(), "You denied permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean getFragmentWorking(){
        // first check if the app has been granted file access permission
        if(checkPermission() == true){
            // TODO - if permission is granted, browse through files in external storage
            // browseFiles();
            fragmentWorking = true;
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

            // call this function to select files from external storage
            browseFiles();
        }
        // if permission is not granted, prompt the user to grant them
        else{
            requestPermission();
        }

        return fragmentWorking;
    }

    private void browseFiles(){
        // set action to open file browser
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // open files that are openable to external apps
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        activityBrowsingResultLauncher.launch(intent);
    }


}