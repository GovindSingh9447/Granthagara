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
import com.ranawat.Admin_Sections.NotesAdminActivity;
import com.ranawat.collagenotes.Adapter.AdapterNotes;
import com.ranawat.collagenotes.Model.ModelNotes;
import com.ranawat.collagenotes.databinding.ActivityNotesUsersBinding;

import java.util.ArrayList;

public class NotesUsersActivity extends AppCompatActivity {

    private ActivityNotesUsersBinding binding;

    public ArrayList<ModelNotes> notesArrayList;
    private com.ranawat.collagenotes.Adapter_Users.AdapterNotes adapterNotes;



    private  String subjectId,subjectTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotesUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //get data from intent
        Intent intent =getIntent();
        subjectId= intent.getStringExtra("subjectId");
        subjectTitle= intent.getStringExtra("subjectTitle");

        loadNotes();



        //search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //search as when user type each word
                try{
                    adapterNotes.getFilter().filter(s);
                }
                catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                        adapterNotes= new com.ranawat.collagenotes.Adapter_Users.AdapterNotes(NotesUsersActivity.this,notesArrayList);
                        binding.notesList.setAdapter(adapterNotes);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}