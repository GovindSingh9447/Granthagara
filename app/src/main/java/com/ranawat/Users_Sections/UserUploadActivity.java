package com.ranawat.Users_Sections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ranawat.Adding_Section.NotesAddActivity;
import com.ranawat.collagenotes.databinding.ActivityUserUploadBinding;

import java.util.HashMap;

public class UserUploadActivity extends AppCompatActivity {

    private ActivityUserUploadBinding binding;

    //firebaseAuth
    private FirebaseAuth auth;

    private Uri notesuri;

    //Progreesbar
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //handel click go back
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //on clicke upload
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatatedata();
            }
        });


        //init firebase auth
        auth=FirebaseAuth.getInstance();



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


    private String title="", descriptions="", collage="", course="", semester="", subject="";

    private void validatatedata() {


        title=binding.titleTv.getText().toString().trim();
        descriptions=binding.descTv.getText().toString().trim();
        collage=binding.collageTv.getText().toString().trim();
        course=binding.courseTv.getText().toString().trim();
        semester=binding.semTv.getText().toString().trim();
        subject=binding.subTv.getText().toString().trim();


        //validate if not empty
        if (TextUtils.isEmpty(subject)){
            Toast.makeText(this, "Please Enter the Subject Name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(collage)){
            Toast.makeText(this, "Please Enter the  Collage/University Name ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(course)){
            Toast.makeText(this, "Please Enter the  Course Name...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(semester)){
            Toast.makeText(this, "Please Enter the  Semester Name ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "Enter the Name of Notes ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(descriptions)){
            Toast.makeText(this, "Enter the Description of notes ...", Toast.LENGTH_SHORT).show();
        }else if(notesuri==null){
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

        String filepathAndName ="Users_Notes/"+timestamp;



        //storage reference
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathAndName);
        storageReference.putFile(notesuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



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


                        Toast.makeText(UserUploadActivity.this, "NOTES upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

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
        hashMap.put("id", ""+timestamp);
        hashMap.put("semesterTitle", ""+semester);
        hashMap.put("subject", ""+subject);
        hashMap.put("courseName", ""+course);
        hashMap.put("collageTitle", ""+collage);
        hashMap.put("timestamp", timestamp);
        hashMap.put("url", ""+uploadeNotesUrl);






        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users_Notes");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(UserUploadActivity.this, "Notes is Uploaded Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserUploadActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


}