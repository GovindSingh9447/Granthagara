package com.ranawat.Interview.InterviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ranawat.Interview.InterviewActivity;
import com.ranawat.Interview.InterviewCollectionActivity;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.collagenotes.databinding.ModelInterviewBinding;

import java.util.ArrayList;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.HolderInterview>{

    private Context context;
    public ArrayList<InterviewModel> modelArrayList;

    private ModelInterviewBinding binding;

    public InterviewAdapter(Context context, ArrayList<InterviewModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public HolderInterview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ModelInterviewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderInterview(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderInterview holder, int position) {

        InterviewModel interviewModel=modelArrayList.get(position);
        String title=interviewModel.getTitle();
        String id=interviewModel.getId();

        //set Data
        holder.title.setText(title);

        Glide.with(context).load(interviewModel.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, InterviewCollectionActivity.class);

                intent.putExtra("title",title);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class  HolderInterview extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;


        public HolderInterview(@NonNull View itemView) {
            super(itemView);

            img=binding.img;
            title=binding.title;

        }
    }
}
