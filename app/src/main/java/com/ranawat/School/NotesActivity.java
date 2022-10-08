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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Interview.InterviewActivity;
import com.ranawat.Interview.InterviewAdapter.InterVCollAdapter;
import com.ranawat.Interview.InterviewCollectionActivity;
import com.ranawat.Interview.InterviewModel.InterVCollModel;
import com.ranawat.School.SchoolAdapter.NotesAdapter;
import com.ranawat.School.SchoolModel.NotesModel;
import com.ranawat.collagenotes.databinding.ActivityNotesBinding;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private ArrayList<NotesModel> notesModelArrayList;
    private NotesAdapter notesAdapter;
    private RecyclerView recycleAnime;
    private GridLayoutManager manager;
    private TextView NotesName;

    String title, id;
    ImageView backBtn;

    ActivityNotesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotesBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());


        backBtn = binding.backBtn;
        NotesName=binding.bookName;

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");

        NotesName.setText(title);
        NotesName.setSelected(true);

        recycleAnime = binding.recycleAnime;
        manager = new GridLayoutManager(this, 2);
        recycleAnime.setLayoutManager(manager);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(NotesActivity.this, SubjectActivity.class);
                startActivity(intent);
                finish();
*/
                onBackPressed();
            }
        });

        loadClassNotes();

    }

    private void loadClassNotes() {
        notesModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("School_Notes");
        reference.orderByChild("subject_id").equalTo(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        notesModelArrayList.clear();

                        Toast.makeText(NotesActivity.this, id, Toast.LENGTH_SHORT).show();
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            // String value=String.valueOf(snapshot.child("animeName").getValue());
                            // System.out.println("NameAnime"+value);




                             NotesModel notesModel=ds.getValue(NotesModel.class);

                            notesModelArrayList.add(notesModel);
                        }
                        notesAdapter = new NotesAdapter(NotesActivity.this, notesModelArrayList);


                        recycleAnime.setAdapter(notesAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}