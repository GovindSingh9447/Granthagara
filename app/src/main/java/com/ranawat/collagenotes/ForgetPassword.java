package com.ranawat.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.ranawat.collagenotes.databinding.ActivityForgetPasswordBinding;

public class ForgetPassword extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    TextInputLayout phone;
    //progressbar
    RelativeLayout progressbar;

    CountryCodePicker countryCodePicker;
    Animation animation;
    ImageView forget_password_icon;
    TextView forget_password_title, forget_password_description;
    Button forgetPasswordNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPassword.this, MakeSelection.class));
                finish();
            }
        });

        animation = AnimationUtils.loadAnimation(ForgetPassword.this, R.anim.side_anim);

        phone = binding.forgetPasswordPhoneNumber;
        progressbar = binding.progressBar;
        countryCodePicker = binding.countryCodePicker;
        forget_password_icon = binding.forgetPasswordIcon;
        forget_password_description = binding.forgetPasswordDescription;
        forget_password_title = binding.forgetPasswordTitle;
        forgetPasswordNextBtn = binding.forgetPasswordNextBtn;

        forget_password_icon.setAnimation(animation);
        forget_password_title.setAnimation(animation);
        forget_password_description.setAnimation(animation);
        phone.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        forgetPasswordNextBtn.setAnimation(animation);
        progressbar.setAnimation(animation);


        forgetPasswordNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking Internet connection
                if (!isConnected(ForgetPassword.this)) {
                    showCustomDialog();
                }

                if (!validatePhoneNumber()) {
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                //get data
                String _phonenumber = phone.getEditText().getText().toString().trim();
                if (_phonenumber.charAt(0) == '0') {
                    _phonenumber = _phonenumber.substring(1);
                }
                final String _completePhoneNo = "+" + countryCodePicker.getFullNumber() + _phonenumber;

                //Database


                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneno").equalTo(_completePhoneNo);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            phone.setError(null);
                            phone.setErrorEnabled(false);


                            Intent intent = new Intent(ForgetPassword.this, VerifyOTP.class);
                            intent.putExtra("phoneNo", _completePhoneNo);
                            intent.putExtra("whatToDo", "updateData");
                            startActivity(intent);
                            finish();

                            progressbar.setVisibility(View.GONE);
                        } else {
                            progressbar.setVisibility(View.GONE);
                            phone.setError("No such user exits!!");
                            phone.requestFocus();
                            Toast.makeText(ForgetPassword.this, "Create New Account...", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }

    private boolean isConnected(ForgetPassword forgetPassword) {


        ConnectivityManager connectivityManager = (ConnectivityManager) forgetPassword.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
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


    private boolean validatePhoneNumber() {
        String val = phone.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phone.setError("Enter valid phone number");
            return false;
        }/* else if (!val.matches(checkspaces)) {
            phone.setError("No White spaces are allowed!");
            return false;
        }*/ else {
            phone.setError(null);
            phone.setErrorEnabled(false);

            return true;
        }
    }


}

