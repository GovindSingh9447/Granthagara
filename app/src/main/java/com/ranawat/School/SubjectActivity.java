package com.ranawat.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.ranawat.Interview.InterviewCollectionActivity;
import com.ranawat.Interview.InterviewModel.InterVCollModel;
import com.ranawat.School.SchoolAdapter.SubjectAdapter;
import com.ranawat.School.SchoolModel.SubjectModel;
import com.ranawat.collagenotes.databinding.ActivitySubjectBinding;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    ActivitySubjectBinding binding;

    //arrayList
    private ArrayList<SubjectModel> subjectModelArrayList;

    //adapter
    private SubjectAdapter subjectAdapter;
    private RecyclerView recycleAnime;
    private GridLayoutManager manager;
    private TextView className;

    String title, id;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySubjectBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        backBtn = binding.backBtn;
        className=binding.classname;

        recycleAnime = binding.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recycleAnime.setLayoutManager(manager);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");

        className.setText(title);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectActivity.this,ClassActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loadSubjects();
    }

    private void loadSubjects() {

        subjectModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Subject");
        reference.orderByChild("f_id").equalTo(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        subjectModelArrayList.clear();

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            // String value=String.valueOf(snapshot.child("animeName").getValue());
                            // System.out.println("NameAnime"+value);


                            SubjectModel subjectModel = ds.getValue(SubjectModel.class);


                            subjectModelArrayList.add(subjectModel);
                        }
                        subjectAdapter = new SubjectAdapter(SubjectActivity.this, subjectModelArrayList);


                        recycleAnime.setAdapter(subjectAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}