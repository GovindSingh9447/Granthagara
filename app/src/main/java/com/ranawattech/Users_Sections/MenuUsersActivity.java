package com.ranawattech.Users_Sections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ranawattech.collagenotes.DashboardAdminActivity;
import com.ranawattech.collagenotes.databinding.ActivityMenuUsersBinding;

import java.util.HashMap;

public class MenuUsersActivity extends AppCompatActivity {

    private ActivityMenuUsersBinding binding;

    //firebaseAuth
    private FirebaseAuth auth;

    private String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMenuUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //init firebase auth
        auth=FirebaseAuth.getInstance();


        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuUsersActivity.this, DashboardAdminActivity.class);
                startActivity(intent);
            }
        });

        //read policy
       binding.rPolicy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MenuUsersActivity.this, UserPolicyActivity.class);
               startActivity(intent);
           }
       });

        binding.adshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareintent=new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                String sharebody="Download this Application now to get your all collage notes here:-https://www.mediafire.com/file/s3pnm5hiz8auljc/app-debug.apk/file&hl=en";
                String sharesub="Collage Notes Apps";

                shareintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                shareintent.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(shareintent,"Share Using"));


            }
        });




        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendmsg();

            }
        });

        binding.adUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MenuUsersActivity.this, UserUploadActivity.class);
                startActivity(intent);

            }
        });





    }

    private void sendmsg() {

        //get msg

        msg=binding.msgBox.getText().toString().trim();


        //if msg is empty
        if (TextUtils.isEmpty(msg)){
            Toast.makeText(this, "Please Enter a message....", Toast.LENGTH_SHORT).show();
        }else
        {
            addmsgToFb();

        }



    }

    private void addmsgToFb() {

        //get timestamp

        long timestamp=System.currentTimeMillis();


        //setup info to firebase db
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("cid", ""+timestamp);
        hashMap.put("message" ,""+msg);
        hashMap.put("timestamp" ,timestamp);
        hashMap.put("uid", ""+auth.getUid());
        hashMap.put("sendername", ""+auth.getCurrentUser());



        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Messages");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(MenuUsersActivity.this, "Message Send Successfully....", Toast.LENGTH_SHORT).show();


                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(MenuUsersActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.msgBox.setText("");

    }
}