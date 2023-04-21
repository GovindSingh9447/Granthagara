package com.ranawattech.collagenotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ranawattech.collagenotes.databinding.ActivitySetNewPasswordBinding;

public class SetNewPassword extends AppCompatActivity {

    ActivitySetNewPasswordBinding binding;

    //progressbar
    RelativeLayout progressbar;
    TextInputLayout setPassword, cPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySetNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressbar = binding.loginProgressBar;
        setPassword=binding.newPassword;
        cPassword=binding.confirmPassword;

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.setNewPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Checking Internet connection
                if (!isConnected(SetNewPassword.this)) {
                    showCustomDialog();
                }


                if (!validateSetPassword() | !validateConfPassword()) {
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                String _newPassword = setPassword.getEditText().getText().toString().trim();
                String _phoneNumber=getIntent().getStringExtra("phoneNo");

                //Update Data in Firebase and in Sessions

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                reference.child(_phoneNumber).child("password").setValue(_newPassword);

                startActivity(new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class));
                finish();


            }
        });
    }

    private boolean validateConfPassword() {
        String val = cPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            cPassword.setError("Field can not be empty");
            return false;
        } /*else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        }*/ else {
            cPassword.setError(null);
            cPassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateSetPassword() {
        String val = setPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            setPassword.setError("Field can not be empty");
            return false;
        } /*else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        }*/ else {
            setPassword.setError(null);
            setPassword.setErrorEnabled(false);
            return true;
        }

    }


    private boolean isConnected(SetNewPassword loginActivity) {


        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please Connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), GetStartedActivity.class));
                        finish();
                    }
                }).show();
    }
}