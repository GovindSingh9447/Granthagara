package com.ranawat.Hindi_Upnayas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ranawat.Hindi_Upnayas.Model.HinUpnModel;
import com.ranawat.Interview.InterviewCollectionActivity;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.collagenotes.databinding.ModelHindiupnBinding;
import com.ranawat.collagenotes.databinding.ModelInterviewBinding;

import java.util.ArrayList;

public class HinUpnAdapter extends RecyclerView.Adapter<HinUpnAdapter.HolderHinUpn> {

    private Context context;
    public ArrayList<HinUpnModel> modelArrayList;

    public HinUpnAdapter(Context context, ArrayList<HinUpnModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    private ModelHindiupnBinding binding;




    @NonNull
    @Override
    public HolderHinUpn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ModelHindiupnBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderHinUpn(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderHinUpn holder, int position) {

        HinUpnModel hinUpnModel=modelArrayList.get(position);
        String title=hinUpnModel.getTitle();
        String author=hinUpnModel.getAuthor();

        //set Data
        holder.title.setText(title);
        holder.author.setText(author);
        Glide.with(context).load(hinUpnModel.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, InterviewCollectionActivity.class);
                intent.putExtra("title",title);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class HolderHinUpn extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title,author;
        public HolderHinUpn(@NonNull View itemView) {
            super(itemView);

            img=binding.img;
            title=binding.title;
            title.setSelected(true);
            author=binding.author;
        }
    }
}
