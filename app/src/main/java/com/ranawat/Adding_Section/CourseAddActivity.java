package com.ranawat.Adding_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.collagenotes.databinding.ActivityCourseAddBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseAddActivity extends AppCompatActivity {


    //view Binding
    private ActivityCourseAddBinding binding;

    //firebaseAuth
    private FirebaseAuth auth;

    //Progreesbar
    private ProgressDialog progressDialog;


    //arraylist to hold course collage
    private ArrayList<String> collageTitleArrayList, collageIdArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCourseAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //handel click go back
        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //init firebase auth
        auth=FirebaseAuth.getInstance();
        
        loadCourseCollage();






        //continue progressbar
        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //handel click pickup collages
        binding.collagenames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collagePickDialog();
            }
        });



        //handel click begin upload course
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();


            }
        });



    }


    //select collage id and collageTitle
    private String selectCollageId, selectedCollageTitle;
    private void collagePickDialog() {


        //get array of collage from collageList
        String[] collageArray =new String[collageTitleArrayList.size()];
        for (int i=0; i<collageTitleArrayList.size();i++){
            collageArray[i]=collageTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Collage/University")
                .setItems(collageArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handel item click
                        //get click item from list
                        selectedCollageTitle= collageTitleArrayList.get(which);
                        selectCollageId=collageIdArrayList.get(which);


                        //set to collage textview
                        binding.collagenames.setText(selectedCollageTitle);

                    }
                })
                .show();
    }

    private void loadCourseCollage() {
        collageTitleArrayList = new ArrayList<>();
        collageIdArrayList = new ArrayList<>();

        //db reference to load collage .... db> collages
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Collages");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                collageTitleArrayList.clear(); //clear before adding data
                collageIdArrayList.clear();


                for (DataSnapshot ds: snapshot.getChildren()){

                    //get collage Id and Name

                    String collageId=""+ds.child("cid").getValue();
                    String collageTitle=""+ds.child("collage").getValue();

                    //add to arraylist
                    collageIdArrayList.add(collageId);
                    collageTitleArrayList.add(collageTitle);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




    private String course="" ;


    private void validateData() {

        //STEP 1:  Validate data

        //Before adding validatation data
        //get data
        course =binding.courseName.getText().toString().trim();



        //validate if not empty
        if (TextUtils.isEmpty(course)){
            Toast.makeText(this, "Please Enter the Course Name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(selectedCollageTitle)){
            Toast.makeText(this, "Pick Collage/University ...", Toast.LENGTH_SHORT).show();
        }else
        {
            addCourseFirebase();
        }
    }

    private void addCourseFirebase() {

        //Step 2: Uploading to fb db

        //show progress
        progressDialog.setMessage("Adding Course....");
        progressDialog.show();

        //get timestamp

        long timestamp=System.currentTimeMillis();

        String uid=auth.getUid();

        //setup info to firebase db
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("cid", ""+timestamp);
        hashMap.put("course" ,""+course);
        hashMap.put("timestamp" ,timestamp);
        hashMap.put("uid", ""+uid);
        hashMap.put("collage", ""+selectCollageId);
        hashMap.put("collageName", ""+selectedCollageTitle);


        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Courses");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(CourseAddActivity.this, "Course Name is added Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(CourseAddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }


}