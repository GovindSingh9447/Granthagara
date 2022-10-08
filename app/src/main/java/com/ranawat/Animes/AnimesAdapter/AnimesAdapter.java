package com.ranawat.Animes.AnimesAdapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.ranawat.Animes.AnimesModel.AnimesModel;
import com.ranawat.Animes.ChaptersActivity;
import com.ranawat.Users_Sections.SemesterUsersActivity;
import com.ranawat.collagenotes.Model.ModelCollage;
import com.ranawat.collagenotes.databinding.ModelAnimeBinding;


import java.security.PrivateKey;
import java.util.ArrayList;

public class AnimesAdapter extends RecyclerView.Adapter<AnimesAdapter.HolderAnimes> {

    private Context context;
    public ArrayList<AnimesModel> modelArrayList;

    public AnimesAdapter(Context context, ArrayList<AnimesModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    private ModelAnimeBinding binding;

    @NonNull
    @Override
    public HolderAnimes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ModelAnimeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderAnimes(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAnimes holder, int position) {

        AnimesModel animesModel = modelArrayList.get(position);
        String title = animesModel.getAnimeName();
        String author = animesModel.getAuthor();
        String artist = animesModel.getArtist();
        String status = animesModel.getStatus();
        String animeId=animesModel.getAnime();
        String img=animesModel.getImg();
        String chapter = animesModel.getTotal_chapters();

        //set Data
        holder.title.setText(title);
        holder.author_name.setText(author);
        holder.status_name.setText(status);
        holder.chapter_name.setText(chapter);
        holder.artist_name.setText(artist);
        Glide.with(context).load(animesModel.getImg()).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChaptersActivity.class);
                intent.putExtra("book", title);
                intent.putExtra("animeId",animeId);
                intent.putExtra("img",img);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    //view Holder class for Binding the Model Book

    class HolderAnimes extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title, author_name, artist_name, status_name, chapter_name;

        public HolderAnimes(@NonNull View itemView) {
            super(itemView);
            title = binding.title;
            title.setSelected(true);
            author_name = binding.authorName;
            artist_name = binding.artistName;
            status_name = binding.status;
            chapter_name = binding.chapterName;
            img = binding.img;


        }
    }

}

//set Data

       /* holder.title.setText(title);
        holder.author_name.setText(author);
        holder.artist_name.setText(artist);
        holder.status_name.setText(status);
        holder.chapter_name.setText(chapter);
        Glide.with(context).load(animesModel.getImgurl()).into(holder.img);*/

            /*img=binding.img;
            title=binding.title;
            artist_name=binding.artistName;
            author_name=binding.authorName;
            status_name=binding.statusName;
            chapter_name=binding.chapterName;
*/