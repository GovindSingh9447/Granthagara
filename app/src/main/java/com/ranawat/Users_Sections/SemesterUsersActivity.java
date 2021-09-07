package com.ranawat.Users_Sections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Admin_Sections.SemesterAdminActivity;
import com.ranawat.collagenotes.Adapter.AdapterSemester;
import com.ranawat.collagenotes.Model.ModelSemester;
import com.ranawat.collagenotes.databinding.ActivitySemesterUsersBinding;

import java.util.ArrayList;

public class SemesterUsersActivity extends AppCompatActivity {

    private ActivitySemesterUsersBinding binding;

    //ArrayList to hold list of data  of type ModelSemester
    private ArrayList<ModelSemester> semesterArrayList;

    //adapter
    private com.ranawat.collagenotes.Adapter_Users.AdapterSemester adapterSemester;

    private String courseId, courseTitle , collageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySemesterUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //get data from intent
        Intent intent =getIntent();
        courseId = intent.getStringExtra("courseId");
        courseTitle = intent.getStringExtra("courseTitle");
        collageName = intent.getStringExtra("collageName");


        //set semester type

        binding.titleTv.setText(courseTitle);
        binding.subtitle.setText(collageName);



        loadSemesterList();



        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //search as when user type each word
                try{
                    adapterSemester.getFilter().filter(s);
                }
                catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //handel click go to previous activity
        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadSemesterList() {


        semesterArrayList =new ArrayList<>();


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Semesters");
        reference.orderByChild("courseId").equalTo(courseId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        semesterArrayList.clear();

                        for (DataSnapshot ds: snapshot.getChildren()){

                            //get data
                            ModelSemester modelSemester=ds.getValue(ModelSemester.class);

                            //add to list
                            semesterArrayList.add(modelSemester);
                        }

                        //setup Adapter
                        adapterSemester = new com.ranawat.collagenotes.Adapter_Users.AdapterSemester(SemesterUsersActivity.this,semesterArrayList);
                        binding.semesterList.setAdapter(adapterSemester);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}