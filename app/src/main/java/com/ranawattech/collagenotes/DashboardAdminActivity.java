package com.ranawattech.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.Adding_Section.Collage_University_Added_Activity;
import com.ranawattech.Adding_Section.NotesAddActivity;
import com.ranawattech.Admin_Sections.AdminMenuActivity;
import com.ranawattech.collagenotes.Adapter.AdapterCollage;
import com.ranawattech.collagenotes.Model.ModelCollage;
import com.ranawattech.collagenotes.databinding.ActivityDashboardAdminBinding;

import java.util.ArrayList;

public class DashboardAdminActivity extends AppCompatActivity {

    //view binding
    private ActivityDashboardAdminBinding binding;

    //Firebase Auth
    private FirebaseAuth firebaseAuth;

    //arraylist to store collage
    private ArrayList<ModelCollage> collageArrayList;

    //adapter
    private AdapterCollage adapterCollage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        
        //init firebase auth
        firebaseAuth =FirebaseAuth.getInstance();
        checkUser();
        loadCollages();


        binding.menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardAdminActivity.this, AdminMenuActivity.class);
                startActivity(intent);
            }
        });


        //edit text lister to search
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //called as and when user type each letter
                try {
                    adapterCollage.getFilter().filter(s);

                }catch(Exception e)
                   {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //handel click logout
        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
                
            }
        });

        //handel click , Start category add screen
        binding.addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdminActivity.this, Collage_University_Added_Activity.class));
            }
        });

        //handel click , start adding pdf
        binding.AddNotesPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdminActivity.this, NotesAddActivity.class));
            }
        });
    }

    private void loadCollages() {

        //init arraylist
        collageArrayList=new ArrayList<>();



        //get all collages from database
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Collages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear arraylist before adding data into it
                collageArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){

                    //get data

                    ModelCollage modelCollage=ds.getValue(ModelCollage.class);

                    //add to arraylist
                    collageArrayList.add(modelCollage);

                }
                //setup adapter
                adapterCollage=new AdapterCollage(DashboardAdminActivity.this,collageArrayList);


                //set adapter to recycle
                binding.collageList.setAdapter(adapterCollage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
            //not login , goto main screen
            startActivity(new Intent(DashboardAdminActivity.this, OnBoardingActivity.class));
            finish();
        }
        else
        {
            //logged in got user info
            String email=firebaseUser.getEmail();

            //get in textview of toolbar
            binding.subtitle.setText(email);
        }
    }
}