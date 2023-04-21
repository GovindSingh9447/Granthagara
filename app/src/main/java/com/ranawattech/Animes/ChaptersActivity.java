package com.ranawattech.Animes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.Animes.AnimesAdapter.ChaptersAdapter;
import com.ranawattech.Animes.AnimesModel.ChaptersModel;
import com.ranawattech.collagenotes.databinding.ActivityChaptersBinding;

import java.util.ArrayList;

public class ChaptersActivity extends AppCompatActivity {
    ActivityChaptersBinding binding;
    private String chId,anime,chapter,animeId, img ;


    private ArrayList<ChaptersModel> chaptersModelArrayList;
    private ChaptersAdapter chaptersAdapter;
    private RecyclerView recycleChapter;
    private GridLayoutManager manager;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChaptersBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        collapsingToolbarLayout=binding.collapsingToolbar;

        //get data from intent
        Intent intent =getIntent();
        anime = intent.getStringExtra("book");
        animeId=intent.getStringExtra("animeId");
        img=intent.getStringExtra("img");


        //Toast.makeText(this, "Lo Dekh lo "+anime+" "+animeId+" "+chapter+" "+img, Toast.LENGTH_SHORT).show();

        Glide.with(this).load(img).into(binding.img);

        collapsingToolbarLayout.setTitle(anime);

        //set data


        recycleChapter=binding.recycleChapter;
        manager=new GridLayoutManager(this,1);
        recycleChapter.setLayoutManager(manager);

       /* binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChaptersActivity.this, AnimesActivity.class));
                finish();
            }
        });*/

        loadChapter();
    }

    private void loadChapter() {
        chaptersModelArrayList=new ArrayList<>();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AnimeChapters");
        reference.orderByChild("animeId").equalTo(animeId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       // Toast.makeText(ChaptersActivity.this, "going to clear arrayList", Toast.LENGTH_SHORT).show();
                        chaptersModelArrayList.clear();
                        //Toast.makeText(ChaptersActivity.this, "DONE to clear arrayList", Toast.LENGTH_SHORT).show();

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            // String value=String.valueOf(snapshot.child("animeName").getValue());
                            // System.out.println("NameAnime"+value);

                            //Toast.makeText(ChaptersActivity.this, "Inside the loop"+ chapter, Toast.LENGTH_SHORT).show();

                            ChaptersModel model=ds.getValue(ChaptersModel.class);

                            //AnimesModel animesModel = ds.getValue(AnimesModel.class);


                            chaptersModelArrayList.add(model);
                        }

                        /*for (DataSnapshot ds:snapshot.getChildren()){
                            //get data
                            Toast.makeText(ChaptersActivity.this, "Inside the loop", Toast.LENGTH_SHORT).show();
                            ChaptersModel model=ds.getValue(ChaptersModel.class);
                            Toast.makeText(ChaptersActivity.this, "going to add arrayList", Toast.LENGTH_SHORT).show();
                            //add to list
                            chaptersModelArrayList.add(model);
                        }*/
                       // Toast.makeText(ChaptersActivity.this, "Hm bahar hai arrayList ke", Toast.LENGTH_SHORT).show();
                        //Set Adapter
                        chaptersAdapter =new ChaptersAdapter(ChaptersActivity.this,chaptersModelArrayList);
                       recycleChapter.setAdapter(chaptersAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(ChaptersActivity.this, "Sorry Anime Id didnt match", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}