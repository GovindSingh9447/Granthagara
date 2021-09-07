package com.ranawat.collagenotes.Adapter_Users;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ranawat.Admin_Sections.NotesAdminActivity;
import com.ranawat.Users_Sections.NotesUsersActivity;
import com.ranawat.collagenotes.Model.ModelSubject;
import com.ranawat.collagenotes.databinding.RowSubjectBinding;
import com.ranawat.collagenotes.databinding.RowSubjectUsersBinding;
import com.ranawat.collagenotes.filter.FilterUsersSemester;
import com.ranawat.collagenotes.filter.FilterUsersSubject;

import java.util.ArrayList;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.HolderSubjectAdmin> implements Filterable {


    //context
    private Context context;

    //arrayList to hold List of subjects
    public ArrayList<ModelSubject> subjectArrayList,filterList;

    //binding
    private  RowSubjectUsersBinding binding;

    private FilterUsersSubject filterUsersSubject;

    public AdapterSubject(Context context, ArrayList<ModelSubject> subjectArrayList) {
        this.context = context;
        this.subjectArrayList = subjectArrayList;
        this.filterList= subjectArrayList;
    }

    @NonNull
    @Override
    public HolderSubjectAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding= RowSubjectUsersBinding.inflate(LayoutInflater.from(context),parent ,false);
        return new HolderSubjectAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSubjectAdmin holder, int position) {

        //get data , set data handel click

        //get data
        ModelSubject modelSubject=subjectArrayList.get(position);
        String title =modelSubject.getSubject();
        String id =modelSubject.getSemesterId();
        String stitle=modelSubject.getSemesterTitle();
        String cctitle =modelSubject.getCourse();
        String subId=modelSubject.getCid();


        //set data
        holder.subjectName.setText(title);







        //handel item click , goto Subject section  also pass semesterId and semesterTitle

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesUsersActivity.class);
                intent.putExtra("courseId",id);
                intent.putExtra("subjectTitle", title);
                intent.putExtra("subjectId", subId);
                context.startActivity(intent);
            }
        });





    }



    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filterUsersSubject==null)
        {
            filterUsersSubject= new FilterUsersSubject(filterList,this);
        }
        return filterUsersSubject;
    }

    //view Holder for row_subject.xml
    class HolderSubjectAdmin extends RecyclerView.ViewHolder{

        //ui view of row_subject.xml
        TextView subjectName;



        public HolderSubjectAdmin(@NonNull View itemView) {
            super(itemView);

            subjectName=binding.subjectName;

        }
    }


}