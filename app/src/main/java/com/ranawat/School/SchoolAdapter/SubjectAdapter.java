package com.ranawat.School.SchoolAdapter;

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
import com.ranawat.School.NotesActivity;
import com.ranawat.School.SchoolModel.SubjectModel;
import com.ranawat.collagenotes.databinding.ModelSubjectBinding;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.subjectHolder> {

    private Context context;
    public ArrayList<SubjectModel> modelArrayList;

    private ModelSubjectBinding binding;
    public SubjectAdapter(Context context, ArrayList<SubjectModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public subjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ModelSubjectBinding.inflate(LayoutInflater.from(context),parent,false);
        return new subjectHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull subjectHolder holder, int position) {

        SubjectModel subjectModel=modelArrayList.get(position);
        String title=subjectModel.getTitle();
        String classs=subjectModel.getClasss();
        String id=subjectModel.getId();

        holder.title.setText(title);
        Glide.with(context).load(subjectModel.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NotesActivity.class);

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


    public class subjectHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;

        public subjectHolder(@NonNull View itemView) {
            super(itemView);

            img=binding.img;
            title=binding.title;

        }
    }

}
