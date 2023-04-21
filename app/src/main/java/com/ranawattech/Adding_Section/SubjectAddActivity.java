package com.ranawattech.Adding_Section;

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
import com.ranawattech.collagenotes.databinding.ActivitySubjectAddBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectAddActivity extends AppCompatActivity {
    //view Binding
    private ActivitySubjectAddBinding binding;

    //firebaseAuth
    private FirebaseAuth auth;

    //Progreesbar
    private ProgressDialog progressDialog;

    //arraylist to hold course collage
    private ArrayList<String> collageTitleArrayList, collageIdArrayList ,courseTitleArrayList, courseIdArrayList, semesterTitleArrayList, semesterIdArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySubjectAddBinding.inflate(getLayoutInflater());
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

        loadSubjectCollage();
        loadSubjectCourse();
        loadSubjectSemester();

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

        //handel click pickup course
        binding.courseNameT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coursePickDialog();
            }
        });


        binding.semesterNameT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semesterPickDialog();
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




    //select semester id and semester Title
    private String selectedSemesterId, selectedSemesterTitle;
    private void semesterPickDialog() {


        //get array of collage from collageList
        String[] semesterArray =new String[semesterTitleArrayList.size()];
        for (int i=0; i<semesterTitleArrayList.size();i++){
            semesterArray[i]=semesterTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Semester")
                .setItems(semesterArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handel item click
                        //get click item from list
                        selectedSemesterTitle= semesterTitleArrayList.get(which);
                        selectedSemesterId=semesterIdArrayList.get(which);


                        //set to semester textview
                        binding.semesterNameT.setText(selectedSemesterTitle);

                    }
                })
                .show();


    }

    //select course id and course Title
    private String selectedCourseId, selectedCourseTitle;
    private void coursePickDialog() {

        //get array of collage from collageList
        String[] courseArray =new String[courseTitleArrayList.size()];
        for (int i=0; i<courseTitleArrayList.size();i++){
            courseArray[i]=courseTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Course")
                .setItems(courseArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handel item click
                        //get click item from list
                        selectedCourseTitle= courseTitleArrayList.get(which);
                        selectedCourseId=courseIdArrayList.get(which);


                        //set to course textview
                        binding.courseNameT.setText(selectedCourseTitle);

                    }
                })
                .show();

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




    private void loadSubjectSemester() {
        semesterIdArrayList =new ArrayList<>();
        semesterTitleArrayList = new ArrayList<>();



        //db reference to load collage .... db> collages
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Semesters");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                semesterTitleArrayList.clear(); //clear before adding data
                semesterIdArrayList.clear();


                for (DataSnapshot ds: snapshot.getChildren()){

                    //get collage Id and Name

                    String semesterId=""+ds.child("cid").getValue();
                    String semesterTitle=""+ds.child("semester").getValue();



                    //add to arraylist
                    semesterIdArrayList.add(semesterId);
                    semesterTitleArrayList.add(semesterTitle);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void loadSubjectCourse() {

        courseTitleArrayList =new ArrayList<>();
        courseIdArrayList =new ArrayList<>();

        //db reference to load course... db >Courses

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear before adding data
                courseTitleArrayList.clear();
                courseIdArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){

                    //get Course Id and name

                    String courseId=""+ds.child("cid").getValue();
                    String courseTitle=""+ds.child("course").getValue();


                    //add to arraylist
                    courseTitleArrayList.add(courseTitle);
                    courseIdArrayList.add(courseId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadSubjectCollage() {
        collageTitleArrayList = new ArrayList<>();
        collageIdArrayList = new ArrayList<>();

        //db reference to load collage .... db> collages
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Collages");
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





    private String subject="" ;
    private void validateData() {


        //STEP 1:  Validate data

        //Before adding validatation data
        //get data
        subject =binding.subjectName.getText().toString().trim();



        //validate if not empty
        if (TextUtils.isEmpty(subject)){
            Toast.makeText(this, "Please Enter the Subject Name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(selectedCollageTitle)){
            Toast.makeText(this, "Pick Collage/University ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(selectedCourseTitle)){
            Toast.makeText(this, "Pick Course ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(selectedSemesterTitle)){
            Toast.makeText(this, "Pick Semseter ...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            addSubjectFirebase();
        }

    }

    private void addSubjectFirebase() {

        //Step 2: Uploading to fb db

        //show progress
        progressDialog.setMessage("Adding Semester....");
        progressDialog.show();

        //get timestamp

        long timestamp=System.currentTimeMillis();

        String uid=auth.getUid();


        //setup info to firebase db
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("cid", ""+timestamp);
        hashMap.put("courseId" ,""+selectedCourseId);
        hashMap.put("course" ,""+selectedCourseTitle);
        hashMap.put("timestamp" ,timestamp);
        hashMap.put("uid", ""+uid);
        hashMap.put("semesterId", ""+selectedSemesterId);
        hashMap.put("semesterTitle", ""+selectedSemesterTitle);
        hashMap.put("subject", ""+subject);
        hashMap.put("collage", ""+selectCollageId);
        hashMap.put("collageName", ""+selectedCollageTitle);




        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Subjects");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(SubjectAddActivity.this, "Subject Name is added Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(SubjectAddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}