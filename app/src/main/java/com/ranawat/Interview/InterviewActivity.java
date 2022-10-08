package com.ranawat.Interview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.ranawat.Interview.InterviewAdapter.InterviewAdapter;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.collagenotes.UserDashboard;
import com.ranawat.collagenotes.databinding.ActivityInterviewBinding;

import java.util.ArrayList;

public class InterviewActivity extends AppCompatActivity {

    ActivityInterviewBinding binding;

    //arrayList
    private ArrayList<InterviewModel> interviewModelArrayList;

    //adapter
    private InterviewAdapter interviewAdapter;
    private RecyclerView recycleAnime;
    private GridLayoutManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInterviewBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        recycleAnime=binding.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recycleAnime.setLayoutManager(manager);

        binding.backBtn.setOnClickListener(v->{
            startActivity(new Intent(InterviewActivity.this, UserDashboard.class));
            finish();
        });


        loadInterviewNotes();
    }

    private void loadInterviewNotes() {
        interviewModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("InterviewSection");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                interviewModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);



                    InterviewModel interviewModel=ds.getValue(InterviewModel.class);


                    interviewModelArrayList.add(interviewModel);
                }
                interviewAdapter = new InterviewAdapter(InterviewActivity.this, interviewModelArrayList);


                recycleAnime.setAdapter(interviewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}