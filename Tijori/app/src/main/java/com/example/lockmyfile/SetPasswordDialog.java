package com.example.lockmyfile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import org.jetbrains.annotations.NotNull;

public class SetPasswordDialog extends AppCompatDialogFragment {

    FrameLayout frameLayout;
    int id;

    String password = "";
    String fakePassword = "";

    /**
     * 1 -> Original
     * 2 -> Set Fake Password
     * 3 -> Change Password
     */

    public SetPasswordDialog(FrameLayout frameLayout, int id){
        this.frameLayout = frameLayout;
        this.id = id;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password", "Password Not Set");
        fakePassword = sharedPreferences.getString("fakePassword", "Password Not Set");
        String notSet = getString(R.string.password_not_set);
        Log.i("Password1", notSet);

        if(password.contentEquals(notSet) || id==2 || id==3){
            return setPasswordDialog(builder, layoutInflater);
        } 
        return loginDialog(builder, layoutInflater);
    }
    
    private AlertDialog setPasswordDialog(AlertDialog.Builder builder, LayoutInflater layoutInflater){
        View setPasswordView = layoutInflater.inflate(R.layout.set_password_dialog, null);

        final EditText passwordTextView = setPasswordView.findViewById(R.id.passwordEditText);
        final EditText confirmPasswordTextView = setPasswordView.findViewById(R.id.confirmPasswordEditText);

        String titleText = (String) ((id == 2)?getText(R.string.fake_password):getText(R.string.original_password));

        AlertDialog dialog = builder.setView(setPasswordView)
                .setTitle(titleText)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton(R.string.confirm_password, null)
                .create();

        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String password_1 = passwordTextView.getText().toString();
                        String password_2 = confirmPasswordTextView.getText().toString();

                        if(id != 2 && password_1.contentEquals(fakePassword)){
                            Toast.makeText(getContext(), "Cannot match with fake password!!!", Toast.LENGTH_SHORT).show();
                        }

                        else if ((password_1.isEmpty() == false) && (password_2.isEmpty() == false) && password_1.contentEquals(password_2)) {
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if(id == 2){
                                editor.putString("fakePassword", password_1);
                            } else{
                                editor.putString("password", password_1);
                            }
                            editor.apply();
                            frameLayout.setVisibility(View.VISIBLE);
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

        Log.i("Password", password);
        View loginView = layoutInflater.inflate(R.layout.login_dialog, null);

        final EditText loginPassword = loginView.findViewById(R.id.passwordTextLogin);

        // TODO forgot password
        final AlertDialog dialog = builder.setView(loginView)
                .setTitle(R.string.login)
                .setNegativeButton(R.string.forgot, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BiometricAuthentication.authenticateUser();
                        SetPasswordDialog changePasswordDialog = new SetPasswordDialog(frameLayout, 3);
                        changePasswordDialog.show(getActivity().getSupportFragmentManager(), "Change Password");
                    }
                })
                .setPositiveButton(R.string.confirm_password, null)
                .create();

        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currPass = loginPassword.getText().toString();

                        if(currPass.contentEquals(fakePassword)){
                            Intent emptyList = new Intent(getActivity(), EmptyLockerActivity.class);
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(emptyList);
                        }
                        else if(loginPassword.getText().toString().contentEquals(password)){
                            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            MainActivity.unlocked = true;
                            dialog.dismiss();
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                        else{
                            Toast.makeText(getContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return dialog;
    }

}
