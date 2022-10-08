package com.ranawat.Animes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Animes.AnimesAdapter.AnimePreviewAdapter;
import com.ranawat.Animes.AnimesModel.AnimePreviewModel;
import com.ranawat.collagenotes.databinding.ActivityAnimeViewBinding;


import java.util.ArrayList;

public class AnimeViewActivity extends AppCompatActivity {

    String anime, animeId,chapterId,chapter;
    ActivityAnimeViewBinding binding;

    private ArrayList<AnimePreviewModel> animePreviewModelsArrayList;
    private AnimePreviewAdapter animePreviewAdapter;
    private GridLayoutManager manager;
    private RecyclerView recycleChapter;
    private TextView chapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAnimeViewBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        chapters=binding.chapter;

        //get data from intent
        Intent intent=getIntent();
        anime=intent.getStringExtra("anime");
        chapterId=intent.getStringExtra("ChapterId");
        chapter=intent.getStringExtra("ChapterNo");

        chapters.setText(chapter);
        chapters.setSelected(true);





       // Toast.makeText(this, "Lo Dekh lo "+anime+" "+chapterId+" "+chapter, Toast.LENGTH_SHORT).show();

        //set data
        recycleChapter=binding.recycleAnimePreview;
        manager=new GridLayoutManager(this,1);
        recycleChapter.setLayoutManager(manager);

        binding.backBtn.setOnClickListener(view -> {

            /*startActivity(new Intent(AnimeViewActivity.this, ChaptersActivity.class));
            finish();*/
            onBackPressed();
        });

        loadAnimePreview();




    }

    String id="1660482167489";
    private void loadAnimePreview() {
        animePreviewModelsArrayList=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AnimeImgs");
        reference.orderByChild("chapterId").equalTo(chapterId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        animePreviewModelsArrayList.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            AnimePreviewModel model=ds.getValue(AnimePreviewModel.class);
                            animePreviewModelsArrayList.add(model);
                        }
                        animePreviewAdapter=new AnimePreviewAdapter(AnimeViewActivity.this,animePreviewModelsArrayList);
                        recycleChapter.setAdapter(animePreviewAdapter );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}