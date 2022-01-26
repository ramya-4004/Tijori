package com.example.lockmyfile.Encryption;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.*;
import java.security.GeneralSecurityException;
public class SharedPreferenceEncryption {

    public static SharedPreferences getSharedPreference(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            String prefFile = "credentials";

            EncryptedSharedPreferences.PrefKeyEncryptionScheme encryptedKey = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV;
            EncryptedSharedPreferences.PrefValueEncryptionScheme encryptedValue = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM;
            if(masterKeyAlias.equals(""))
                return null;
            return EncryptedSharedPreferences.create(
                    prefFile,
                    masterKeyAlias,
                    context,
                    encryptedKey,
                    encryptedValue
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static EncryptedFile encryptCopiedFile(File file, Context context) {



        EncryptedFile.FileEncryptionScheme fileEncryptionScheme = EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB;

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    context,
                    masterKeyAlias,
                    fileEncryptionScheme
            ).build();
            return encryptedFile;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap decryptImage(File file, Context context) {
        EncryptedFile.FileEncryptionScheme fileEncryptionScheme = EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB;
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    context,
                    masterKeyAlias,
                    fileEncryptionScheme
            ).build();
            InputStream stream = encryptedFile.openFileInput();
            return BitmapFactory.decodeStream(stream);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File decryptVideoOrPdf(File file, Context context){
        EncryptedFile.FileEncryptionScheme fileEncryptionScheme = EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB;

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    context,
                    masterKeyAlias,
                    fileEncryptionScheme
            ).build();
            InputStream stream = encryptedFile.openFileInput();
            File videoFile = new File(context.getFilesDir().getPath() + "/decrypted video");
            copyFile(stream, videoFile);
            return videoFile;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(InputStream ios, File destination) {

        try {
            OutputStream os = new FileOutputStream(destination);
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
}
