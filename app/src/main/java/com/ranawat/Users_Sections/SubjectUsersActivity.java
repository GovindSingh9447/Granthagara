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
import com.ranawat.collagenotes.Adapter.AdapterSubject;
import com.ranawat.collagenotes.Model.ModelSubject;
import com.ranawat.collagenotes.databinding.ActivitySubjectUsersBinding;

import java.util.ArrayList;

public class SubjectUsersActivity extends AppCompatActivity {
    private ActivitySubjectUsersBinding binding;

    //arraylist to hold list of data  of type ModelSubject
    private ArrayList<ModelSubject> subjectArrayList;

    //adapter
    private com.ranawat.collagenotes.Adapter_Users.AdapterSubject adapterSubject;

    private String  courseName , collageName, semesterId, semestername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySubjectUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        //get data from intent
        Intent intent =getIntent();
        semesterId = intent.getStringExtra("semesterId");
        semestername = intent.getStringExtra("semesterTitle");
        collageName = intent.getStringExtra("collageName");
        courseName = intent.getStringExtra("courseName");

        //set semester type

        binding.titleTv.setText(semestername);
        binding.subtitle.setText(courseName);


        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //search as when user type each word
                try{
                    adapterSubject.getFilter().filter(s);
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


        loadSubjectList();
    }

    private void loadSubjectList() {


        subjectArrayList= new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Subjects");
        reference.orderByChild("semesterId").equalTo(semesterId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        subjectArrayList.clear();

                        for (DataSnapshot ds: snapshot.getChildren()){

                            //get data
                            ModelSubject modelSubject=ds.getValue(ModelSubject.class);

                            //add to list
                            subjectArrayList.add(modelSubject);
                        }

                        //setup Adapter
                        adapterSubject = new com.ranawat.collagenotes.Adapter_Users.AdapterSubject(SubjectUsersActivity.this,subjectArrayList);
                        binding.subjectList.setAdapter(adapterSubject);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}