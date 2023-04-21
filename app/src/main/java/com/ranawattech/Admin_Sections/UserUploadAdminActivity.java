package com.ranawattech.Admin_Sections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.collagenotes.Adapter_Users.AdapterUsersUpload;
import com.ranawattech.collagenotes.Model.ModelUserUpload;
import com.ranawattech.collagenotes.databinding.ActivityUserUploadAdminBinding;

import java.util.ArrayList;

public class UserUploadAdminActivity extends AppCompatActivity {
    private ActivityUserUploadAdminBinding binding;

    private ArrayList<ModelUserUpload> arrayList;
    private AdapterUsersUpload adapterUsersUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserUploadAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadUserUploadedNotes();
    }

    private void loadUserUploadedNotes() {
        arrayList= new ArrayList<>();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users_Notes");
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){

                            //get data
                            ModelUserUpload modelUserUpload = ds.getValue(ModelUserUpload.class);

                            //add to list
                            arrayList.add(modelUserUpload);
                        }
                        adapterUsersUpload= new AdapterUsersUpload(UserUploadAdminActivity.this, arrayList);
                        binding.notesList.setAdapter(adapterUsersUpload);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}