package com.ranawat.School;

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
import com.ranawat.Interview.InterviewActivity;
import com.ranawat.Interview.InterviewAdapter.InterviewAdapter;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.School.SchoolAdapter.ClassAdapter;
import com.ranawat.School.SchoolModel.ClassModel;
import com.ranawat.collagenotes.UserDashboard;
import com.ranawat.collagenotes.databinding.ActivityClassBinding;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    ActivityClassBinding binging;

    private ArrayList<ClassModel> classModelArrayList;
    private ClassAdapter classAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binging=ActivityClassBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(binging.getRoot());

        recyclerView=binging.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        binging.backBtn.setOnClickListener(v->{
            startActivity(new Intent(ClassActivity.this, UserDashboard.class));
            finish();
        });


        loadClass();
    }

    private void loadClass() {
        classModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("School");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);



                    ClassModel classModel=ds.getValue(ClassModel.class);


                    classModelArrayList.add(classModel);
                }
                classAdapter = new ClassAdapter(ClassActivity.this, classModelArrayList);


                recyclerView.setAdapter(classAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}