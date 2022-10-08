package com.ranawat.Animes;

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
import com.ranawat.Adding_Section.CourseAddActivity;
import com.ranawat.collagenotes.databinding.ActivityAddChapterBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AddChapterActivity extends AppCompatActivity {

    ActivityAddChapterBinding binding;
    //firebaseAuth
    private FirebaseAuth auth;

    //Progreesbar
    private ProgressDialog progressDialog;


    //arraylist to hold course collage
    private ArrayList<String> animeTitleArrayList, animeIdArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddChapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //handel click go back
        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadAnime();

        //continue progressbar
        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);


        //handel click pickup collages
        binding.collagenames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animePickDialog();
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

    private String chapter="" ;
    private void validateData() {

        chapter=binding.courseName.getText().toString().trim();


        //validate if not empty
        if (TextUtils.isEmpty(chapter)){
            Toast.makeText(this, "Please Enter the chapter Number...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(selectedAnimeTitle)){
            Toast.makeText(this, "Pick Collage/University ...", Toast.LENGTH_SHORT).show();
        }else
        {
            addChapterFirebase();
        }
    }

    private void addChapterFirebase() {
        //Step 2: Uploading to fb db

        //show progress
        progressDialog.setMessage("Adding Course....");
        progressDialog.show();

        //get timestamp

        long timestamp=System.currentTimeMillis();

        //String uid=auth.getUid();

        //setup info to firebase db
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("cid", ""+timestamp);
        hashMap.put("chapter" ,"Chapter "+chapter);
        hashMap.put("timestamp" ,timestamp);
       // hashMap.put("uid", ""+uid);
        hashMap.put("animeId", ""+selectAnimeId);
        hashMap.put("animeName", ""+selectedAnimeTitle);


        //upload to fb db
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AnimeChapters");
        reference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Collage add successful
                        progressDialog.dismiss();
                        Toast.makeText(AddChapterActivity.this, "Course Name is added Successfully....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(AddChapterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }


    //select collage id and collageTitle
    private String selectAnimeId, selectedAnimeTitle;
    private void animePickDialog() {

        //get anime
        String[] animeArray=new String[animeTitleArrayList.size()];
        for (int i=0; i<animeTitleArrayList.size();i++){
            animeArray[i]=animeTitleArrayList.get(i);
        }

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick Anime")
                .setItems(animeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        selectedAnimeTitle=animeTitleArrayList.get(i);
                        selectAnimeId=animeIdArrayList.get(i);

                        //set to anime textview
                        binding.collagenames.setText(selectedAnimeTitle);
                    }
                }).show();

    }

    private void loadAnime() {
        animeTitleArrayList=new ArrayList<>();
        animeIdArrayList=new ArrayList<>();

        //db reffernace to load college  ....db> anime
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Animes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                animeTitleArrayList.clear();
                animeIdArrayList.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    //get anime Id and Name

                    String animeId=""+ds.child("anime").getValue();
                    String animeTile=""+ds.child("animeName").getValue();

                    //add to arraylist
                    animeIdArrayList.add(animeId);
                    animeTitleArrayList.add(animeTile);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}