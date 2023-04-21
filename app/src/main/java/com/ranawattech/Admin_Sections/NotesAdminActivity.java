package com.ranawattech.Admin_Sections;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.collagenotes.Adapter.AdapterNotes;
import com.ranawattech.collagenotes.Model.ModelNotes;
import com.ranawattech.collagenotes.databinding.ActivityNotesAdminBinding;

import java.util.ArrayList;


public class NotesAdminActivity extends AppCompatActivity {

    private ActivityNotesAdminBinding binding;
    private ArrayList<ModelNotes> notesArrayList;
    private AdapterNotes adapterNotes;



    private  String subjectId,subjectTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotesAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get data from intent
        Intent intent =getIntent();
        subjectId= intent.getStringExtra("subjectId");
        subjectTitle= intent.getStringExtra("subjectTitle");
        
        loadNotes();

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.subtitle.setText(subjectTitle);



    }

    private void loadNotes() {

        notesArrayList = new ArrayList<>();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notes");
        reference.orderByChild("subjectId").equalTo(subjectId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        notesArrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){

                            //get data
                            ModelNotes modelNotes = ds.getValue(ModelNotes.class);

                            //add to list
                            notesArrayList.add(modelNotes);
                        }
                        adapterNotes=new AdapterNotes(NotesAdminActivity.this,notesArrayList);
                        binding.notesList.setAdapter(adapterNotes);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}