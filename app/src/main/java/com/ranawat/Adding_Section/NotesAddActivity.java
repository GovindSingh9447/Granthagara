package com.ranawat.Adding_Section;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ranawat.Admin_Sections.UserUploadAdminActivity;
import com.ranawat.Notifications.FcmNotificationsSender;

import com.ranawat.collagenotes.DashboardUserActivity;
import com.ranawat.collagenotes.MainActivity;
import com.ranawat.collagenotes.databinding.ActivityNotesAddBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class NotesAddActivity extends AppCompatActivity {


    //view Binding
    private ActivityNotesAddBinding binding;

    //firebaseAuth
    private FirebaseAuth auth;

    private Uri notesuri;

    //Progreesbar
    private ProgressDialog progressDialog;





    //Tag for debugging
    private static final String TAG="ADD_NOTES_FILE";


    //arraylist to hold course collage
    private ArrayList<String> collageTitleArrayList, collageIdArrayList ,courseTitleArrayList, courseIdArrayList, semesterTitleArrayList, semesterIdArrayList , subjectTitleArrayList, subjectIdArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotesAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // for sending notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("all");


        //handel click go back
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.ViewUserNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesAddActivity.this, UserUploadAdminActivity.class);
                startActivity(intent);
            }
        });


        //init firebase auth
        auth=FirebaseAuth.getInstance();

        loadNotesCollage();
        loadNotesCourse();
        loadNotesSemester();
        loadNotesSubject();



        //continue progressbar
        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);




        //handel click attach notes
        binding.attachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesPickIntent();
            }
        });




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



        binding.SubjectNameT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectPickDialog();
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

    private void notesPickIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==86 && resultCode ==RESULT_OK && data!=null)
        {
            notesuri=data.getData();
            Toast.makeText(this, "Notes is selected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please select the Notes.....", Toast.LENGTH_SHORT).show();
        }

    }

    //select subject id and subject Title
    private String selectedSubjectId, selectedSubjectTitle;
    private void subjectPickDialog() {

        //get array of collage from collageList
        String[] subjectArray =new String[subjectTitleArrayList.size()];
        for (int i=0; i<subjectTitleArrayList.size();i++){
            subjectArray[i]=subjectTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Subject")
                .setItems(subjectArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handel item click
                        //get click item from list
                        selectedSubjectTitle= subjectTitleArrayList.get(which);
                        selectedSubjectId=subjectIdArrayList.get(which);


                        //set to semester textview
                        binding.SubjectNameT.setText(selectedSubjectTitle);

                    }
                })
                .show();

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

    private void loadNotesCollage() {

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

    private void loadNotesCourse() {

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

    private void loadNotesSemester() {
        semesterIdArrayList =new ArrayList<>();
        semesterTitleArrayList = new ArrayList<>();


        //db reference to load collage .... db> collages
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Semesters");
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
    private void loadNotesSubject() {

        subjectIdArrayList = new ArrayList<>();
        subjectTitleArrayList = new ArrayList<>();

        //db reference to load subject

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Subjects");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear before adding data
                subjectTitleArrayList.clear();
                subjectIdArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    String subjectId =""+ds.child("cid").getValue();
                    String subjectTitle=""+ds.child("subject").getValue();


                    subjectIdArrayList.add(subjectId);
                    subjectTitleArrayList.add(subjectTitle);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private String title="", descriptions="", collage="", course="", semester="", subject="",senderName="";

    private void validateData() {
        //setp 1:

        title=binding.titleTv.getText().toString().trim();
        descriptions=binding.descTv.getText().toString().trim();
        senderName=binding.senderTv.getText().toString().trim();
        collage=binding.collagenames.getText().toString().trim();
        course=binding.courseNameT.getText().toString().trim();
        semester=binding.semesterNameT.getText().toString().trim();
        subject=binding.SubjectNameT.getText().toString().trim();


        //validate if not empty
        if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "Enter the Name of Notes ...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(descriptions)){
            Toast.makeText(this, "Enter the Description of notes ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(senderName)){
            Toast.makeText(this, "Please enter the sender Name, if there is no sender then just write 'Known' ...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(subject)){
            Toast.makeText(this, "Please Enter the Subject Name...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(semester)){
            Toast.makeText(this, "Pick Semseter ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(course)){
            Toast.makeText(this, "Pick Course ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(collage)){
            Toast.makeText(this, "Pick Collage/University ...", Toast.LENGTH_SHORT).show();
        }
        else if(notesuri==null){
            Toast.makeText(this, "Pick the Notes ...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            uploadNotesToStorage();
        }


    }

    private void uploadNotesToStorage() {

        //step 2  :Upload notes to Storage

        progressDialog.setMessage("Uploading Notes....");
        progressDialog.show();

        //timestamp
        long timestamp =System.currentTimeMillis();


        //path of notes in firebase storage

        String filepathAndName ="Notes/"+timestamp;

        //storage reference
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathAndName);
        storageReference.putFile(notesuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.d(TAG , "OnSuccess: NOTES uploaded to storage....");
                        Log.d(TAG , "OnSuccess: getting notes url");

                        //get notes url
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadeNotesUrl=""+uriTask.getResult();

                        //upload to firebase db
                        uploadNotesInfoToDb(uploadeNotesUrl,timestamp);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG , "OnFailure: NOTES upload failed due to"+e.getMessage());
                        Toast.makeText(NotesAddActivity.this, "NOTES upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void uploadNotesInfoToDb(String uploadeNotesUrl, long timestamp) {
        //step 3  : upload to firebase db

        progressDialog.setMessage("Uploading Notes Info....");

        String uid=auth.getUid();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("title", ""+title);
        hashMap.put("descriptions", ""+descriptions);
        hashMap.put("senderName", ""+senderName);
        hashMap.put("id", ""+timestamp);
        hashMap.put("semesterId", ""+selectedSemesterId);
        hashMap.put("semesterTitle", ""+semester);
        hashMap.put("subject", ""+subject);
        hashMap.put("courseName", ""+course);
        hashMap.put("subjectId", ""+selectedSubjectId);
        hashMap.put("collageTitle", ""+collage);
        hashMap.put("timestamp", timestamp);
        hashMap.put("url", ""+uploadeNotesUrl);



        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notes");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(NotesAddActivity.this, "Notes is Uploaded Successfully....", Toast.LENGTH_SHORT).show();

                       sendNotification();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(NotesAddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });







    }
    String titles="New Notes Uploaded";
    String body="Notes of "+ collage+" - "+course+" - "+semester+" - "+subject+" - "+title;

    private void sendNotification() {
        /*FcmNotificationsSender notificationsSender=new FcmNotificationsSender("topics/all",
                titles,
                body,
                getApplicationContext(), MainActivity.this);
        notificationsSender.SendNotifications();*/
    }


}