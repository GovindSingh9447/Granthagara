package com.ranawattech.entrance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.collagenotes.UserDashboard;
import com.ranawattech.collagenotes.databinding.ActivityEntranceBinding;
import com.ranawattech.entrance.EntranceAdapter.EntranceAdapter;
import com.ranawattech.entrance.EntranceModel.EntranceModel;

import java.util.ArrayList;

public class EntranceActivity extends AppCompatActivity {

    ActivityEntranceBinding binding;

    private ArrayList<EntranceModel> entranceModelArrayList;

    //adapter
    private EntranceAdapter entranceAdapter;
    private RecyclerView recycleAnime;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEntranceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recycleAnime=binding.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recycleAnime.setLayoutManager(manager);

        binding.backBtn.setOnClickListener(v->{
            startActivity(new Intent(EntranceActivity.this, UserDashboard.class));
            finish();
        });


        loadEntranceNotes();




    }

    private void loadEntranceNotes() {

        entranceModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("EntranceSection");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entranceModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);



                    EntranceModel entranceModel=ds.getValue(EntranceModel.class);


                    entranceModelArrayList.add(entranceModel);
                }
                entranceAdapter = new EntranceAdapter(EntranceActivity.this, entranceModelArrayList);


                recycleAnime.setAdapter(entranceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}