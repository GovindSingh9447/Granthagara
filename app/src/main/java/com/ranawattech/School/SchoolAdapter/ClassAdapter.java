package com.ranawattech.School.SchoolAdapter;

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
import com.ranawattech.School.SchoolModel.ClassModel;
import com.ranawattech.School.SubjectActivity;
import com.ranawattech.collagenotes.databinding.ModelClassBinding;
import com.ranawattech.collagenotes.databinding.ModelInterviewBinding;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.HolderClass> {

    private Context context;
    public ArrayList<ClassModel> modelArrayList;
    private ModelClassBinding binding;

    public ClassAdapter(Context context, ArrayList<ClassModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @NonNull
    @Override
    public HolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      binding=ModelClassBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderClass(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderClass holder, int position) {
        ClassModel classModel=modelArrayList.get(position);
        String title=classModel.getTitle();
        String id=classModel.getId();

        holder.title.setText(title);
        Glide.with(context).load(classModel.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SubjectActivity.class);

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

    public class HolderClass extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;


        public HolderClass(@NonNull View itemView) {
            super(itemView);

            img=binding.img;
            title=binding.title;
        }
    }

}
