package com.ranawat.Interview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Interview.InterviewAdapter.InterVCollAdapter;
import com.ranawat.Interview.InterviewAdapter.InterviewAdapter;
import com.ranawat.Interview.InterviewModel.InterVCollModel;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.collagenotes.databinding.ActivityInterviewCollectionBinding;

import java.util.ArrayList;

public class InterviewCollectionActivity extends AppCompatActivity {
    ActivityInterviewCollectionBinding binding;

    //arrayList
    private ArrayList<InterVCollModel> interVCollModelArrayList;

    //adapter
    private InterVCollAdapter interVCollAdapter;
    private RecyclerView recycleAnime;
    private GridLayoutManager manager;
    private TextView bookName;

    String title, id;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInterviewCollectionBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        backBtn = binding.backBtn;
        bookName=binding.bookName;

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");

       bookName.setText(title);
       bookName.setSelected(true);

        recycleAnime = binding.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recycleAnime.setLayoutManager(manager);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterviewCollectionActivity.this, InterviewActivity.class);

               /* Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(backBtn, "transition_group_interview");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InterviewCollectionActivity.this, pairs);
                startActivity(intent, options.toBundle()); */

                startActivity(intent);
                finish();

            }
        });

        loadInterviewNotes();

    }

    private void loadInterviewNotes() {
        interVCollModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Interview_Notes");
        reference.orderByChild("f_id").equalTo(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        interVCollModelArrayList.clear();

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            // String value=String.valueOf(snapshot.child("animeName").getValue());
                            // System.out.println("NameAnime"+value);


                            InterVCollModel interVCollModel = ds.getValue(InterVCollModel.class);


                            interVCollModelArrayList.add(interVCollModel);
                        }
                        interVCollAdapter = new InterVCollAdapter(InterviewCollectionActivity.this, interVCollModelArrayList);


                        recycleAnime.setAdapter(interVCollAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}