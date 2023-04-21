package com.ranawattech.School;

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
import android.widget.ImageView;
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
import com.ranawattech.collagenotes.databinding.ActivityUploadSchoolDataBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadSchoolDataActivity extends AppCompatActivity {
    ActivityUploadSchoolDataBinding binding;
    ImageView pdf,subjectImg,classImg;
    //Progreesbar
    private ProgressDialog progressDialog;
    //Tag for debugging
    private static final String TAG="ADD_NOTES_FILE";
    private Uri filePath, PdfPath;

    private ArrayList<String> classTitleArrayList, classIdArrayList,subjectTitleArrayList, subjectIdArrayList,subjectClassArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadSchoolDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        classImg=binding.classN;
        subjectImg=binding.subject;
        pdf=binding.pdf;


        //continue progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);


        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UploadSchoolDataActivity.this, UserDashboard.class);
                startActivity(intent);
                finish();
            }
        });
        classImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImgForClass();
            }
        });
        subjectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImgForSubject();
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNotes();
            }
        });
        binding.addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationClass();
            }
        });
        binding.addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationSubject();
            }
        });
        binding.notesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePDF();
            }
        });
        binding.selectClassSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classPickDialog();
            }
        });
        binding.selectClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classPickDialog();
            }
        });
        binding.selectSubjectnames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectPickDialog();
            }
        });

        loadClass();
        loadSubject();

    }


    public String selectClassId, selectClassTitle;
    private void classPickDialog() {

        //get array of interview group from list
        String[] interviewArray = new String[classTitleArrayList.size()];
        for(int i=0;i<classTitleArrayList.size();i++){
            interviewArray[i]=classTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Class")
                .setItems(interviewArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectClassTitle=classTitleArrayList.get(which);
                        selectClassId=classIdArrayList.get(which);

                        //set to text view



                        binding.selectClassSub.setText(selectClassTitle);
                        binding.selectClass.setText(selectClassTitle);
                    }
                })
                .show();
    }



    private void loadClass() {
        classIdArrayList=new ArrayList<>();
        classTitleArrayList=new ArrayList<>();


        //db ref to load interview
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("School");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear before adding data
                classIdArrayList.clear();
                classTitleArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    String interviewId=""+ds.child("id").getValue();
                    String interviewTitle=""+ds.child("title").getValue();



                    classIdArrayList.add(interviewId);
                    classTitleArrayList.add(interviewTitle);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public String selectSubjectId, selectSubjectTitle ,className;
    private void subjectPickDialog() {

        //get array of interview group from list
        String[] interviewArray = new String[subjectTitleArrayList.size()];
        for(int i=0;i<subjectTitleArrayList.size();i++){
            interviewArray[i]=subjectTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Subject")
                .setItems(interviewArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectSubjectTitle=subjectTitleArrayList.get(which);
                        selectSubjectId=subjectIdArrayList.get(which);
                        className=subjectClassArrayList.get(which);

                        //set to text view

                        binding.selectSubjectnames.setText(selectSubjectTitle+"_"+className);
                    }
                })
                .show();
    }


    private void loadSubject() {


        subjectIdArrayList=new ArrayList<>();
        subjectTitleArrayList=new ArrayList<>();
        subjectClassArrayList=new ArrayList<>();

        //db ref to load interview
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Subject");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear before adding data
                subjectIdArrayList.clear();
                subjectTitleArrayList.clear();
                subjectClassArrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    String interviewId=""+ds.child("id").getValue();
                    String interviewTitle=""+ds.child("title").getValue();
                    String className=""+ds.child("classs").getValue();

                    subjectIdArrayList.add(interviewId);
                    subjectTitleArrayList.add(interviewTitle);
                    subjectClassArrayList.add(className);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void selectNotes() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectImgForSubject() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectImgForClass() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

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

    //##########################################################

    String classs="";
    private void validationClass() {
        classs = binding.classname.getText().toString().trim();

        if (TextUtils.isEmpty(classs)){
            Toast.makeText(this, "Please Enter Class...", Toast.LENGTH_SHORT).show();
        }
        else if(filePath == null){
            Toast.makeText(this, "Pick Image ...", Toast.LENGTH_SHORT).show();
        }else
        {
            UploadClassStorage();
        }

    }

    String SubjectName="",  select_classnames;
    private void validationSubject() {
        SubjectName = binding.subjectname.getText().toString().trim();
        select_classnames=binding.selectClass.getText().toString().trim();
        // sourceName=binding.sourceName.getText().toString().trim();

        if (TextUtils.isEmpty(SubjectName)){
            Toast.makeText(this, "Please Enter Subject ...", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(select_classnames)){
            Toast.makeText(this, "Pick Class Group....", Toast.LENGTH_SHORT).show();
        }
        else if(filePath == null){
            Toast.makeText(this, "Pick Img ...", Toast.LENGTH_SHORT).show();
        }else
        {
            uploadToSubjStorage();
        }
    }

    String notesName="",  selectClass,sourceName="", selectSubjectnames;
    private void validatePDF() {
        notesName = binding.notesName.getText().toString().trim();
        selectClass=binding.selectClass.getText().toString().trim();
        selectSubjectnames=binding.selectClass.getText().toString().trim();
        sourceName=binding.sourceName.getText().toString().trim();

        if (TextUtils.isEmpty(notesName)){
            Toast.makeText(this, "Please Enter Notes Name...", Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(sourceName)){
            Toast.makeText(this, "Enter Source Name....", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(selectClass)){
            Toast.makeText(this, "Pick Class Group....", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(selectSubjectnames)){
            Toast.makeText(this, "Pick Subject Group....", Toast.LENGTH_SHORT).show();
        }
        else if(filePath == null){
            Toast.makeText(this, "Pick PDF ...", Toast.LENGTH_SHORT).show();
        }else
        {
            uploadToPDFStorage();
        }
    }


    private void UploadClassStorage() {

        classs = binding.classname.getText().toString().trim();
        progressDialog.setMessage("Uploading Notes....");
        progressDialog.show();

        long timestamp=System.currentTimeMillis();

        //path
        String filepathAndName="School_Notes/"+"Class_Preview/"+classs+"_"+timestamp;
        //storage reference
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathAndName);
        storageReference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        //get notes url
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadeClassUrl=""+uriTask.getResult();

                        //upload to firebase db
                        uploadClassInfoToFireBase(uploadeClassUrl,timestamp);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(UploadSchoolDataActivity.this, "Class upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    String subjectName;
    private void uploadToSubjStorage() {
        //step 2  :Upload notes to Storage

        progressDialog.setMessage("Uploading Notes....");
        progressDialog.show();

        //timestamp
        long timestamp =System.currentTimeMillis();

        select_classnames=binding.selectClass.getText().toString().trim();
        subjectName = binding.subjectname.getText().toString().trim();



        // replace space
        subjectName= subjectName.replaceAll("\\s","");

        //path of notes in firebase storage
        String filepathAndName ="School_Notes/"+select_classnames+"/"+subjectName+""+timestamp;

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
                        String uploadeSubjectUrl=""+uriTask.getResult();

                        //upload to firebase db
                        uploadSubjectInfoToFireBase(uploadeSubjectUrl,timestamp);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG , "OnFailure: NOTES upload failed due to"+e.getMessage());
                        Toast.makeText(UploadSchoolDataActivity.this, "NOTES upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }



    private void uploadToPDFStorage() {

        //step 2  :Upload notes to Storage

        progressDialog.setMessage("Uploading Notes....");
        progressDialog.show();

        //timestamp
        long timestamp =System.currentTimeMillis();

        selectClass=binding.selectClass.getText().toString().trim();
        selectSubjectnames=binding.selectSubjectnames.getText().toString().trim();
        notesName = binding.notesName.getText().toString().trim();



        // replace space
       // notesN= notesName.replaceAll("\\s","");

        //path of notes in firebase storage
        String filepathAndName ="School_Notes/"+selectClass+"/"+selectSubjectnames+"/"+notesName+timestamp;

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
                        Toast.makeText(UploadSchoolDataActivity.this, "NOTES upload failed due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void addpdfFirebase(String uploadeNotesUrl, long timestamp) {


        progressDialog.setMessage("Uploading Notes Info....");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("title", ""+notesName);
        hashMap.put("sourceName", ""+sourceName);
        hashMap.put("class_id", ""+selectClassId);
        hashMap.put("subject_id",""+selectSubjectId);
        hashMap.put("subject", ""+selectSubjectnames);
        hashMap.put("classs",""+selectClass);
        hashMap.put("pdf", ""+uploadeNotesUrl);
        // hashMap.put("uid", ""+uid);

        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("School_Notes");
        reference.child(""+selectClass+"_"+selectSubjectnames+"_"+notesName+"_"+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(UploadSchoolDataActivity.this, "Notes is Uploaded Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UploadSchoolDataActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }

    private void uploadClassInfoToFireBase(String uploadeClassUrl, long timestamp) {

        progressDialog.setMessage("Uploading Notes Info....");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("title", ""+classs);
        hashMap.put("img", ""+uploadeClassUrl);
        // hashMap.put("uid", ""+uid);

        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("School");
        reference.child(""+classs+"_"+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(UploadSchoolDataActivity.this, "Class is Added Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UploadSchoolDataActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void uploadSubjectInfoToFireBase(String uploadeSubjectUrl, long timestamp) {

        progressDialog.setMessage("Uploading Notes Info....");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("f_id", ""+selectClassId);
        hashMap.put("title", ""+subjectName);
        hashMap.put("classs",""+select_classnames);
        hashMap.put("img", ""+uploadeSubjectUrl);
        // hashMap.put("uid", ""+uid);

        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Subject");
        reference.child(""+select_classnames+"_"+subjectName+"_"+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(UploadSchoolDataActivity.this, "Notes is Uploaded Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UploadSchoolDataActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }

}