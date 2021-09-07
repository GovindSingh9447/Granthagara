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
import com.ranawat.Admin_Sections.CourseAdminActivity;
import com.ranawat.collagenotes.Adapter_Users.AdapterCourse;
import com.ranawat.collagenotes.Model.ModelCourse;
import com.ranawat.collagenotes.databinding.ActivityCoureseUsersBinding;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class CourseUsersActivity extends AppCompatActivity {

    private ActivityCoureseUsersBinding binding;

    private  String collageId, collageTitle;
    //ArrayList to hold list of data
    private ArrayList<ModelCourse> courseArrayList;

    private AdapterCourse adapterCourse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCoureseUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //get data from list
        Intent intent=getIntent();
        collageId = intent.getStringExtra("CollageId");
        collageTitle = intent.getStringExtra("CollageTitle");

        //set course collage
        binding.subtitle.setText(collageTitle);
        binding.titleTv.setText("Courses");


        loadcourselist();


        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //search as when user type each word
                try{
                    adapterCourse.getFilter().filter(s);
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

    private void loadcourselist() {

        //init list before adding data
        courseArrayList =new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Courses");
        ref.orderByChild("collage").equalTo(collageId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courseArrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            //get data
                            ModelCourse modelCourse=ds.getValue(ModelCourse.class);
                            //add to list
                            courseArrayList.add(modelCourse);
                        }


                        //setup adapter
                        adapterCourse = new AdapterCourse(CourseUsersActivity.this,courseArrayList);
                        binding.courseList.setAdapter(adapterCourse);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





    }
}