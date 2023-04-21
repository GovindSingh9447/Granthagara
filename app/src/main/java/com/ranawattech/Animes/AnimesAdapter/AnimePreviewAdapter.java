package com.ranawattech.Animes.AnimesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.ranawattech.Animes.AnimesModel.AnimePreviewModel;
import com.ranawattech.collagenotes.databinding.ModelAnimeImgPreviewBinding;

import java.util.ArrayList;

public class AnimePreviewAdapter extends RecyclerView.Adapter<AnimePreviewAdapter.HolderAnimePreview> {

    private Context context;
    public ArrayList<AnimePreviewModel> modelArrayList;

    public AnimePreviewAdapter(Context context, ArrayList<AnimePreviewModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    private ModelAnimeImgPreviewBinding binding;

    @NonNull
    @Override
    public HolderAnimePreview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding=ModelAnimeImgPreviewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderAnimePreview(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAnimePreview holder, int position) {

        AnimePreviewModel animePreviewModel=modelArrayList.get(position);
        String chapterId=animePreviewModel.getChapterId();
        String img=animePreviewModel.getUrl_1();


        //set data
        Glide.with(context).load(animePreviewModel.getUrl_1()).into(holder.anime_preview);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class HolderAnimePreview extends RecyclerView.ViewHolder{

        PhotoView anime_preview;
        public HolderAnimePreview(@NonNull View itemView) {
            super(itemView);

            anime_preview= binding.animePreview;
        }
    }
}
