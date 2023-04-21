package com.ranawattech.collagenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ranawattech.collagenotes.databinding.ActivityOnBoardingBinding;
import com.ranawattech.collagenotes.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {


    //view Binding
    private ActivityRegisterBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //init firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        //setup progress dailog
        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);


        //handel click , back button

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        scrollView=binding.scrollView;

        //handel click , begin register

        binding.regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }

        });

    }

    private  String name= "", email="",number="", password="" , countrycode="+91";
    private void validateData() {
        /* Before Creating account , lets do some data vaildation */


        //get data
        name =binding.nameEt.getText().toString().trim();
        email =binding.emailEt.getText().toString().trim();
        number=binding.phoneEt.getText().toString().trim();
        password =binding.passwordEt.getText().toString().trim();
        String cPassword = binding.cpasswordEt.getText().toString().trim();

        String phoneno=countrycode+number;


        //vaildate data
        if(TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this, "Enter you name.....", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(RegisterActivity.this, "Invalid email Pattern.....", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(number)){
            Toast.makeText(RegisterActivity.this, "Enter Phone Number.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "Enter Password....!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cPassword)){
            Toast.makeText(RegisterActivity.this, "Confirm Password....!", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(cPassword)){
            Toast.makeText(RegisterActivity.this, "Password doesn't match.... ", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Intent intent=new Intent (getApplicationContext(),VerifyOTP.class);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("phoneNo",phoneno);
            intent.putExtra("password",password);
            startActivity(intent);

            Pair[] pairs=new Pair[1];
            pairs[0] =new Pair<View , String >(scrollView,"teansition_Otp_screen");


           // createuserAccount();
        }

    }

   /* private void createuserAccount() {
        progressDialog.setMessage("Creating Account....");
        progressDialog.dismiss();

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account creation is successful now the data in firebase
                        updateUserInfo();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //acount creating failed
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void updateUserInfo() {
        progressDialog.setMessage("Saving Information...");

        //TimeStamp
        long timestamp =System.currentTimeMillis();

        //Creating Uid
        String uid =firebaseAuth.getUid();

        //setup data to add in db
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("uid",uid);
        hashMap.put("email", email);
        hashMap.put("name",name);
        hashMap.put("password",password);
        hashMap.put("Profileimage","");
        hashMap.put("userType", "user"); //option: User or Admin  :: I will add admin manualliy by db
        hashMap.put("timestamp",timestamp);


        //set data to db
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(aVoid -> {
                    //data added to db
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Account Created...", Toast.LENGTH_SHORT).show();
                    //starting new activity
                    startActivity(new Intent(RegisterActivity.this, DashboardUserActivity.class ));
                    finish();

                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                });



    }
*/

}