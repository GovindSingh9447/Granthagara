package com.ranawattech.Animes.AnimesAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ranawattech.Animes.AnimeViewActivity;
import com.ranawattech.Animes.AnimesModel.ChaptersModel;
import com.ranawattech.collagenotes.databinding.ModelChapterBinding;

import java.util.ArrayList;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.HolderAnimes> {

    private Context context;
    public ArrayList<ChaptersModel> modelArrayList;

    public ChaptersAdapter(Context context, ArrayList<ChaptersModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    private ModelChapterBinding binding;



    @NonNull
    @Override
    public HolderAnimes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ModelChapterBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ChaptersAdapter.HolderAnimes(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAnimes holder, int position) {

        ChaptersModel model=modelArrayList.get(position);
        String chapter=model.getChapter();
        String dec=model.getDec();
        String like=model.getLike();
        String animeId=model.getAnimeId();
        String chId=model.getCid();
        String anime=model.getAnimeName();

        //Toast.makeText(context, "one"+chId+""+chapter, Toast.LENGTH_SHORT).show();

        holder.chapter.setText(chapter);
        holder.total_like.setText(like);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AnimeViewActivity.class);
                intent.putExtra("ChapterId",chId);
                intent.putExtra("anime",anime);
                intent.putExtra("animeId",animeId);
                intent.putExtra("ChapterNo",chapter);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class HolderAnimes extends RecyclerView.ViewHolder {

     TextView chapter_des,chapter,total_like;


        public HolderAnimes(@NonNull View itemView) {
            super(itemView);

            chapter= binding.chapter;
            chapter_des= binding.chapterDes;
            total_like= binding.totalLike;

        }
    }
}
