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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.Users_Sections.MenuUsersActivity;
import com.ranawattech.collagenotes.Model.ModelCollage;
import com.ranawattech.collagenotes.databinding.ActivityDashboardUserBinding;

import java.util.ArrayList;

public class DashboardUserActivity extends AppCompatActivity {


    //view binding
    private ActivityDashboardUserBinding binding;

    //Firebase Auth
    private FirebaseAuth firebaseAuth;

    //arraylist to store collage
    private ArrayList<ModelCollage> collageArrayList;

    String email, name, Cphoneno;


    //adapter
    private com.ranawattech.collagenotes.Adapter_Users.AdapterCollage adapterCollage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        Cphoneno = getIntent().getStringExtra("phoneNo");


        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUsers();
        loadCollages();


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

                } catch (Exception e) {

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

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardUserActivity.this, GetStartedActivity.class));
                finish();

            }
        });

        binding.menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardUserActivity.this, MenuUsersActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadCollages() {

        //init arraylist
        collageArrayList = new ArrayList<>();


        //get all collages from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Collages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //cle ar arraylist before adding data into it
                collageArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {

                    //get data

                    ModelCollage modelCollage = ds.getValue(ModelCollage.class);

                    //add to arraylist
                    collageArrayList.add(modelCollage);

                }
                //setup adapter
                adapterCollage = new com.ranawattech.collagenotes.Adapter_Users.AdapterCollage(DashboardUserActivity.this, collageArrayList);


                //set adapter to recycle
                binding.collageList.setAdapter(adapterCollage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void checkUsers() {
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneno").equalTo(Cphoneno);

        /*checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String email1 = snapshot.child(Cphoneno).child("email").getValue(String.class);


                binding.subtitle.setText(email1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                startActivity(new Intent(DashboardUserActivity.this, GetStartedActivity.class));
                finish();
            }
        });*/

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
            //not login , goto main screen
            startActivity(new Intent(DashboardUserActivity.this, GetStartedActivity.class));
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