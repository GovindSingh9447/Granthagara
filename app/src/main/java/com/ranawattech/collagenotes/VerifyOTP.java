package com.ranawattech.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ranawattech.collagenotes.databinding.ActivityVerifyOtpBinding;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    ActivityVerifyOtpBinding binding;
    PinView pinView;
    String codeBySystem;


    String email, password, name, whatToDo, phoneno;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //setup progress dailog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerifyOTP.this, SetNewPassword.class);
                startActivity(intent);
                finish();
            }
        });*/

        pinView = binding.pinView;

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phoneno = getIntent().getStringExtra("phoneNo");
        password = getIntent().getStringExtra("password");
        whatToDo = getIntent().getStringExtra("whatToDo");

        binding.otpDescriptionText.setText("Enter One Time Password Sent On " + phoneno);

        sendVerificationCodeToUser(phoneno);


    }

    private void sendVerificationCodeToUser(String phoneno) {


        // [START start_phone_auth]
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth) //mAuth is defined on top
                .setPhoneNumber(phoneno)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pinView.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        //codeBySystem -- this is provide by the system
        //code  -- this is written by the by the user

        //PhoneAuthCredential -- this will automatically check the both the code weather they are same or not.


        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

     FirebaseAuth auth = FirebaseAuth.getInstance();

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String user=auth.getUid();
                            Toast.makeText(VerifyOTP.this, user+"Verification Completed...", Toast.LENGTH_SHORT).show();
                            /*Intent intent=new Intent(VerifyOTP.this,DashboardUserActivity.class);
                            startActivity(intent);
                            finish();*/


                           // createuserAccount();
                            updateUserInfo();

                           /* //for forgetPassword
                            if (whatToDo.equals("updateData")) {
                                updateOldUserData();
                            }else
                            {
                                *//*createuserAccount();
                                updateUserInfo();*//*
                            }*/


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUserData() {

        Intent intent=new Intent(getApplicationContext(),SetNewPassword.class);
        intent.putExtra("phoneNo",phoneno);
        startActivity(intent);
        finish();
    }

    private void updateUserInfo() {

        progressDialog.setMessage("Saving Information...");

        //TimeStamp
        long timestamp = System.currentTimeMillis();

        //Creating Uid
        String uid = auth.getUid();

        //setup data to add in db
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("phoneno", phoneno);
        hashMap.put("name", name);
        hashMap.put("password", password);
        hashMap.put("Profileimage", "");
        hashMap.put("userType", "user"); //option: User or Admin  :: I will add admin manualliy by db
        hashMap.put("timestamp", timestamp);


        //set data to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .child(phoneno)
                .setValue(hashMap)
                .addOnSuccessListener(aVoid -> {
                    //data added to db
                    progressDialog.dismiss();
                    Toast.makeText(VerifyOTP.this, "Account Created...", Toast.LENGTH_SHORT).show();
                    //starting new activity
                    startActivity(new Intent(VerifyOTP.this, UserDashboard.class));
                    finish();

                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(VerifyOTP.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                });


    }

    private void createuserAccount() {

        progressDialog.setMessage("Creating Account....");
        progressDialog.dismiss();

        //create user in firebase auth
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account creation is successful now the data in firebase


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //acount creating failed
                        progressDialog.dismiss();
                        Toast.makeText(VerifyOTP.this, e.getMessage() + "Update your email in Profile section", Toast.LENGTH_SHORT).show();

                    }
                });


    }


    //first check the call and redirect user accordingly to the profile or to the reset password screen
    public void Verify(View view) {

        String code = pinView.getText().toString();

        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}