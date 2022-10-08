package com.ranawat.Animes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Animes.AnimesAdapter.AnimesAdapter;
import com.ranawat.Animes.AnimesModel.AnimesModel;
import com.ranawat.collagenotes.UserDashboard;
import com.ranawat.collagenotes.databinding.ActivityAnimesBinding;

import java.util.ArrayList;

public class AnimesActivity extends AppCompatActivity {

    ActivityAnimesBinding binding;

    //Firebase Auth
    // private FirebaseAuth firebaseAuth;

    //arrayList
    private ArrayList<AnimesModel> animesModelArrayList;

    //adapter
    private AnimesAdapter animesAdapter;
    private RecyclerView recycleAnime;
    private GridLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimesBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        recycleAnime=binding.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recycleAnime.setLayoutManager(manager);

        binding.backBtn.setOnClickListener(view -> {

            startActivity(new Intent(AnimesActivity.this, UserDashboard.class));
            finish();
        });

        loadAnimes();
    }

    private void loadAnimes() {
        //init arrayList

        animesModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Animes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animesModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);


                    AnimesModel animesModel = ds.getValue(AnimesModel.class);


                    animesModelArrayList.add(animesModel);
                }
                animesAdapter = new AnimesAdapter(AnimesActivity.this, animesModelArrayList);

                recycleAnime.setAdapter(animesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}