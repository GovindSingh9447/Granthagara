package com.ranawat.Hindi_Upnayas;

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
import com.ranawat.Animes.AnimesActivity;
import com.ranawat.Animes.AnimesAdapter.AnimesAdapter;
import com.ranawat.Animes.AnimesModel.AnimesModel;
import com.ranawat.Hindi_Upnayas.Adapter.HinUpnAdapter;
import com.ranawat.Hindi_Upnayas.Model.HinUpnModel;
import com.ranawat.collagenotes.UserDashboard;
import com.ranawat.collagenotes.databinding.ActivityHindiUpnayasBinding;

import java.util.ArrayList;

public class HindiUpnayasActivity extends AppCompatActivity {

    ActivityHindiUpnayasBinding binding;
    //arrayList
    private ArrayList<HinUpnModel> hinUpnModelArrayList;

    //adapter
    private HinUpnAdapter hinUpnAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHindiUpnayasBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        recyclerView=binding.recycleHindiUpn;
        manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        binding.backBtn.setOnClickListener(view -> {

            startActivity(new Intent(HindiUpnayasActivity.this, UserDashboard.class));
            finish();
        });

        loadHindUpn();

    }

    private void loadHindUpn() {

        //init arrayList

        hinUpnModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hindi_Upanyas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hinUpnModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);


                    HinUpnModel hinUpnModel = ds.getValue(HinUpnModel.class);


                    hinUpnModelArrayList.add(hinUpnModel);
                }
                hinUpnAdapter = new HinUpnAdapter(HindiUpnayasActivity.this, hinUpnModelArrayList);

                recyclerView.setAdapter(hinUpnAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}