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
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.ranawat.Admin_Sections.UserListActivity;
import com.ranawat.collagenotes.databinding.ActivityLoginBinding;

import java.security.Policy;

public class LoginActivity extends AppCompatActivity {


    //view binding
    private ActivityLoginBinding binding;

    //firebase Auth
    private FirebaseAuth firebaseAuth;

    //progressbar
    RelativeLayout progressbar;

    TextInputLayout phone;
    TextInputLayout password;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //init firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //setup progressdialog

        progressbar = binding.loginProgressBar;
        phone = binding.userphone;
        countryCodePicker=binding.countryCodePicker;
       // password = binding.userPassword;

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MakeSelection.class);
                startActivity(intent);
                finish();
            }
        });

        //handel click , begin login
        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Checking Internet connection
                if (!isConnected(LoginActivity.this)) {
                    showCustomDialog();
                }

                if (!validatePhoneNumber()  /* | !validatePassword()*/) {
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                //get data
                String _phonenumber = phone.getEditText().getText().toString().trim();
                //String _password = password.getEditText().getText().toString().trim();
                if (_phonenumber.charAt(0) == '0') {
                    _phonenumber = _phonenumber.substring(1);
                }
                String _completePhoneNo = "+"+countryCodePicker + _phonenumber;

                //Database

                String uid=firebaseAuth.getUid();

                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild(uid).orderByChild("phoneno").equalTo(_completePhoneNo);



                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            phone.setError(null);
                            phone.setErrorEnabled(false);

                            startActivity(new Intent(LoginActivity.this, UserDashboard.class));

                          //  String systemPassword = snapshot.child(_completePhoneNo).child("password").getValue(String.class);

                           /* if (systemPassword.equals(_password)) {
                                password.setError(null);
                                password.setErrorEnabled(false);


                                String _name = snapshot.child(_completePhoneNo).child("name").getValue(String.class);
                                String _email = snapshot.child(_completePhoneNo).child("email").getValue(String.class);
                                String userType = snapshot.child(_completePhoneNo).child("userType").getValue(String.class);


                                Intent intent = new Intent(LoginActivity.this, UserDashboard.class);
                                intent.putExtra("name",_name);
                                intent.putExtra("email",_email);
                                intent.putExtra("phoneNo",_completePhoneNo);
                               // intent.putExtra("whatToDo","updateData");
                                startActivity(intent);
                                finish();

                                get user type
                                startActivity(new Intent(LoginActivity.this, DashboardUserActivity.class));
                                finish();


                                //check user type
                                if (userType.equals("user")) {
                                    //this is the simple user open the user Dashboard
                                    startActivity(new Intent(LoginActivity.this, DashboardUserActivity.class));
                                    finish();

                                } else if (userType.equals("admin")) {
                                    //this the Admin , Open the Admin Dashboard
                                    startActivity(new Intent(LoginActivity.this, DashboardAdminActivity.class));
                                    finish();
                                }

                            } else {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Password Does not match...", Toast.LENGTH_SHORT).show();
                            }*/
                        } else {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "User Does Not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        CheckBox rememberMe = binding.rememberMe;

        if (rememberMe.isChecked()) {

        }

    }

    private boolean isConnected(LoginActivity loginActivity) {


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
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
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
            password.setError("Field can not be empty");
            return false;
        } /*else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        }*/ else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


  /*  private void loginuser() {

        //login User

        firebaseAuth.signInWithEmailAndPassword(phone,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login successful ,check if user is user or Admin
                        checkuser();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }*/

    private void checkuser() {

        // check if user is user or Admin
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //check in db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseUser.getPhoneNumber())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        //get user type
                        String userType = "" + snapshot.child("userType").getValue();

                        //check user type
                        if (userType.equals("user")) {
                            //this is the simple user open the user Dashboard
                            startActivity(new Intent(LoginActivity.this, DashboardUserActivity.class));
                            finish();

                        } else if (userType.equals("admin")) {
                            //this the Admin , Open the Admin Dashboard
                            startActivity(new Intent(LoginActivity.this, DashboardAdminActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}