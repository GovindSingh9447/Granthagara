package com.ranawattech.Interview;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.ranawattech.collagenotes.UserDashboard;
import com.ranawattech.collagenotes.databinding.ActivityUploadInterviewBinding;


import java.util.ArrayList;
import java.util.HashMap;

public class UploadInterviewActivity extends AppCompatActivity {

    ActivityUploadInterviewBinding binding;
    //Progreesbar
    private ProgressDialog progressDialog;
    //Tag for debugging
    private static final String TAG="ADD_NOTES_FILE";

    //request ccode
    private final int PICK_IMAGE_REQUEST = 22;

    private Uri filePath, PdfPath;
    //firebaseAuth

    private ArrayList<String> interviewTitleArrayList , interviewIdArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadInterviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
      //  auth=FirebaseAuth.getInstance();


        //continue progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(UploadInterviewActivity.this, UserDashboard.class);
               startActivity(intent);
               finish();
            }
        });

        binding.bookpreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        binding.notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            validationNotes();
            }
        });

        binding.choosePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesPickIntent();
            }
        });

        binding.selectBooksnames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interviewPickDialog();
            }
        });

        loadInterview();

    }


    //group --2
    public String selectInterviewId, selectInterviewTitle;
    private void interviewPickDialog() {

        //get array of interview group from list
        String[] interviewArray = new String[interviewTitleArrayList.size()];
        for(int i=0;i<interviewTitleArrayList.size();i++){
            interviewArray[i]=interviewTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Interview Section")
                .setItems(interviewArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectInterviewTitle=interviewTitleArrayList.get(which);
                        selectInterviewId=interviewIdArrayList.get(which);

                        //set to text view

                        binding.selectBooksnames.setText(selectInterviewTitle);
                    }
                })
                .show();
    }
    private void loadInterview() {
        interviewIdArrayList=new ArrayList<>();
        interviewTitleArrayList=new ArrayList<>();

        //db ref to load interview
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("InterviewSection");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear before adding data
                interviewIdArrayList.clear();
                interviewTitleArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    String interviewId=""+ds.child("id").getValue();
                    String interviewTitle=""+ds.child("title").getValue();

                    interviewIdArrayList.add(interviewId);
                    interviewTitleArrayList.add(interviewTitle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //group--1
    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    //group -- 2
    private void notesPickIntent() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    //group--1  or group -- 2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==86 && resultCode ==RESULT_OK && data!=null)
        {
            filePath=data.getData();
            Toast.makeText(this, "Notes is selected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please select the Notes.....", Toast.LENGTH_SHORT).show();
        }

    }


    //gropup -- 2
    String notesName="", sourceNames="",select_booksnames;
    private void validationNotes() {
        notesName = binding.notesName.getText().toString().trim();
        sourceNames=binding.sourceName.getText().toString().trim();
        select_booksnames=binding.selectBooksnames.getText().toString().trim();

        if (TextUtils.isEmpty(notesName)){
            Toast.makeText(this, "Please Enter Book Number...", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(sourceNames)){
            Toast.makeText(this, "Enter Source Name....", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(select_booksnames)){
            Toast.makeText(this, "Pick Interview Group....", Toast.LENGTH_SHORT).show();
        }
        else if(filePath == null){
            Toast.makeText(this, "Pick PDF ...", Toast.LENGTH_SHORT).show();
        }else
        {
            uploadToInterviewStorage();
        }
    }

    //group--1
    String book = "";
    private void validation() {
        book = binding.bookname.getText().toString().trim();

        if (TextUtils.isEmpty(book)){
            Toast.makeText(this, "Please Enter Book Number...", Toast.LENGTH_SHORT).show();
        }
        else if(filePath == null){
            Toast.makeText(this, "Pick Image ...", Toast.LENGTH_SHORT).show();
        }else
        {
            addBookFirebase();
        }

    }

    //group--1  -- Storage
    private void addBookFirebase() {

        book = binding.bookname.getText().toString().trim();
        progressDialog.setMessage("Uploading Notes....");
        progressDialog.show();

        long timestamp=System.currentTimeMillis();

        //path
        String filepathAndName="Interviews_Notes/"+"Book_Preview/"+book+timestamp;
        //storage reference
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathAndName);
        storageReference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        //get notes url
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadeNotesUrl=""+uriTask.getResult();

                        //upload to firebase db
                        uploadBookInfoToDb(uploadeNotesUrl,timestamp);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(UploadInterviewActivity.this, "NOTES upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //group--1   --realtime
    private void uploadBookInfoToDb(String uploadeNotesUrl, long timestamp) {

        progressDialog.setMessage("Uploading Notes Info....");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("title", ""+book);
        hashMap.put("img", ""+uploadeNotesUrl);
       // hashMap.put("uid", ""+uid);

        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("InterviewSection");
        reference.child(""+book+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(UploadInterviewActivity.this, "Notes is Uploaded Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UploadInterviewActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    String notesN;
    private void uploadToInterviewStorage() {

        //step 2  :Upload notes to Storage

        progressDialog.setMessage("Uploading Notes....");
        progressDialog.show();

        //timestamp
        long timestamp =System.currentTimeMillis();

        select_booksnames=binding.selectBooksnames.getText().toString().trim();
        notesName = binding.notesName.getText().toString().trim();



        // replace space
        notesN= notesName.replaceAll("\\s","");

        //path of notes in firebase storage
        String filepathAndName ="Interviews_Notes/"+select_booksnames+"/"+notesN+timestamp;

        //storage reference
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathAndName);
        storageReference.putFile(filePath)
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
                        addpdfFirebase(uploadeNotesUrl,timestamp);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG , "OnFailure: NOTES upload failed due to"+e.getMessage());
                        Toast.makeText(UploadInterviewActivity.this, "NOTES upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void addpdfFirebase(String uploadeNotesUrl, long timestamp) {

        progressDialog.setMessage("Uploading Notes Info....");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("f_id", ""+selectInterviewId);
        hashMap.put("title", ""+notesName);
        hashMap.put("group",""+select_booksnames);
        hashMap.put("source",""+sourceNames);
        hashMap.put("pdf", ""+uploadeNotesUrl);
        // hashMap.put("uid", ""+uid);

        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Interview_Notes");
        reference.child(""+select_booksnames+"_"+notesName+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(UploadInterviewActivity.this, "Notes is Uploaded Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UploadInterviewActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }

}
