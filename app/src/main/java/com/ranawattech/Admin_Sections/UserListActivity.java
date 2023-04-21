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
import com.ranawattech.collagenotes.Adapter.AdapterUserList;
import com.ranawattech.collagenotes.Model.ModelUserList;
import com.ranawattech.collagenotes.databinding.ActivityUserListBinding;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private ActivityUserListBinding binding;

    private ArrayList<ModelUserList> arrayList;

    private AdapterUserList adapterUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadUserList();


    }

    private void loadUserList() {
        arrayList= new ArrayList<>();



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayList.clear();

                        for (DataSnapshot ds: snapshot.getChildren()){

                            //get data
                            ModelUserList model=ds.getValue(ModelUserList.class);

                            //add to list
                            arrayList.add(model);
                        }

                        //setup Adapter
                        adapterUserList =new AdapterUserList(UserListActivity.this,arrayList);
                        binding.userList.setAdapter(adapterUserList);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}