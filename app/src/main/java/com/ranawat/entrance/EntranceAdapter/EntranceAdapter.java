package com.ranawat.entrance.EntranceAdapter;

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
import com.ranawat.Interview.InterviewAdapter.InterviewAdapter;
import com.ranawat.Interview.InterviewCollectionActivity;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.collagenotes.databinding.ModelEntranceBinding;
import com.ranawat.collagenotes.databinding.ModelInterviewBinding;
import com.ranawat.entrance.EntranceModel.EntranceModel;

import java.util.ArrayList;

public class EntranceAdapter extends RecyclerView.Adapter<EntranceAdapter.EntranceHolder> {

    private Context context;
    public ArrayList<EntranceModel> modelArrayList;

    private ModelEntranceBinding binding;
    public EntranceAdapter(Context context, ArrayList<EntranceModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public EntranceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ModelEntranceBinding.inflate(LayoutInflater.from(context),parent,false);
        return new EntranceHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull EntranceHolder holder, int position) {


        EntranceModel entranceModel=modelArrayList.get(position);

        String title=entranceModel.getTitle();
        String id=entranceModel.getId();

        //set Data
        holder.title.setText(title);

        Glide.with(context).load(entranceModel.getImg()).into(holder.img);

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

    public class EntranceHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;

        public EntranceHolder(@NonNull View itemView) {
            super(itemView);

            img=binding.img;
            title=binding.title;
        }
    }
}
