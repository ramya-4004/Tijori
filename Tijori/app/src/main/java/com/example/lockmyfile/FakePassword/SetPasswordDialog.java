package com.example.lockmyfile.FakePassword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.lockmyfile.Encryption.SharedPreferenceEncryption;
import com.example.lockmyfile.R;
import org.jetbrains.annotations.NotNull;

public class SetPasswordDialog extends AppCompatDialogFragment {

    String fakePassword = "";

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        SharedPreferences sharedPreferences = SharedPreferenceEncryption.getSharedPreference(getContext().getApplicationContext());
        fakePassword = sharedPreferences.getString("fakePassword", "Password Not Set");
        String notSet = getString(R.string.password_not_set);
        Log.i("Password1", notSet);

        if(fakePassword.contentEquals(notSet)){
            return setPasswordDialog(builder, layoutInflater);
        } 
        return loginDialog(builder, layoutInflater);

    }

    private AlertDialog setPasswordDialog(AlertDialog.Builder builder, LayoutInflater layoutInflater){
        View setPasswordView = layoutInflater.inflate(R.layout.set_password_dialog, null);

        final EditText passwordTextView = setPasswordView.findViewById(R.id.passwordEditText);
        final EditText confirmPasswordTextView = setPasswordView.findViewById(R.id.confirmPasswordEditText);


        AlertDialog dialog = builder.setView(setPasswordView)
                .setTitle(getText(R.string.original_password))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton(R.string.confirm_password, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String password_1 = passwordTextView.getText().toString();
                        String password_2 = confirmPasswordTextView.getText().toString();

                        if ((password_1.isEmpty() == false) && (password_2.isEmpty() == false) && password_1.contentEquals(password_2)) {
                            SharedPreferences sharedPreferences = SharedPreferenceEncryption.getSharedPreference(getContext().getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("fakePassword", password_1);
                            editor.apply();
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getContext(), "Password fields either empty or mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    private AlertDialog loginDialog(AlertDialog.Builder builder, LayoutInflater layoutInflater){

        View loginView = layoutInflater.inflate(R.layout.login_dialog, null);

        final EditText loginPassword = loginView.findViewById(R.id.passwordTextLogin);

        final AlertDialog dialog = builder.setView(loginView)
                .setTitle(R.string.login)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton(R.string.confirm_password, null)
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currPass = loginPassword.getText().toString();

                        if(currPass.contentEquals(fakePassword)){
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                            loginPassword.setText("");
                        }
                    }
                });
            }
        });

        return dialog;
    }


}
