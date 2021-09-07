package com.ranawat.Admin_Sections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.collagenotes.Adapter_Users.AdapterMessage;
import com.ranawat.collagenotes.Model.ModelMessages;
import com.ranawat.collagenotes.databinding.ActivityUserSentMessagesBinding;

import java.util.ArrayList;

public class UserSentMessagesActivity extends AppCompatActivity {

    private ActivityUserSentMessagesBinding binding;

    private ArrayList<ModelMessages> arrayList;

    private AdapterMessage adapterMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserSentMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        loadMessage();
    }

    private void loadMessage() {

        arrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    ModelMessages modelMessages=ds.getValue(ModelMessages.class);

                    //add to list

                    arrayList.add(modelMessages);
                }

                //setup Adapter
                adapterMessage= new AdapterMessage(UserSentMessagesActivity.this, arrayList);
                binding.collageList.setAdapter(adapterMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}