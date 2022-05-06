package com.ranawat.Adding_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.ranawat.Admin_Sections.UserSentMessagesActivity;
import com.ranawat.collagenotes.databinding.ActivityCollageUniversityAddedBinding;

import java.util.HashMap;

public class Collage_University_Added_Activity extends AppCompatActivity {
    //view Binding
    private ActivityCollageUniversityAddedBinding binding;

    //firebaseAuth
    private FirebaseAuth auth;
    //Progreesbar
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCollageUniversityAddedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        auth=FirebaseAuth.getInstance();

        //configure firebase
        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);


        binding.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Collage_University_Added_Activity.this, UserSentMessagesActivity.class);
                startActivity(intent);
            }
        });

        //handel click go back
        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //handel on click on btn course
        binding.adCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Collage_University_Added_Activity.this, CourseAddActivity.class));
            }
        });

        //handel on click on btn course
        binding.adSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Collage_University_Added_Activity.this, SemesterAddActivity.class));
            }
        });

        //handel on click on btn course
        binding.adsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Collage_University_Added_Activity.this, SubjectAddActivity.class));
            }
        });


        //handel click begin upload cat

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        

    }

    private String collage="";
    private void validateData() {

        //Before adding validatation data


        //get data
         collage =binding.collageName.getText().toString().trim();
         
         //validate if not empty
        if (TextUtils.isEmpty(collage)){
            Toast.makeText(this, "Please Enter the name of Collage or University...", Toast.LENGTH_SHORT).show();
        }else
        {
            addCollageFirebase();
        }
    }

    private void addCollageFirebase() {
        //show progress
        progressDialog.setMessage("Adding Collage....");
        progressDialog.show();

        //get timestamp

        long timestamp=System.currentTimeMillis();

        //setup info to firebase db
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("cid", ""+timestamp);
        hashMap.put("collage" ,""+collage);
        hashMap.put("timestamp" ,timestamp);
        hashMap.put("uid", ""+auth.getUid());


        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Collages");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(Collage_University_Added_Activity.this, "Collage Name is added Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(Collage_University_Added_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}