package com.ranawattech.collagenotes.Adapter_Users;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ranawattech.Users_Sections.SemesterUsersActivity;
import com.ranawattech.collagenotes.Model.ModelCourse;
import com.ranawattech.collagenotes.databinding.RowCourseBinding;
import com.ranawattech.collagenotes.databinding.RowCourseUsersBinding;
import com.ranawattech.collagenotes.filter.FilterUsersCourse;

import java.util.ArrayList;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.HolderCourseAdmin> implements Filterable{

    //context
    private Context context;

    //arrayList to hold list data of type course
    public ArrayList<ModelCourse> courseArrayList  ,filterList;

    //view binding row_course.xml
    private @NonNull RowCourseUsersBinding binding;

    private FilterUsersCourse filterUsersCourse;





    //constructor of above
    public AdapterCourse(Context context, ArrayList<ModelCourse> courseArrayList) {
        this.context = context;
        this.courseArrayList = courseArrayList;
        this.filterList=courseArrayList;

    }

    @NonNull
    @Override
    public HolderCourseAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //binding layout using view binding
        binding= RowCourseUsersBinding.inflate(LayoutInflater.from(context),parent,false);

        return new HolderCourseAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCourseAdmin holder, int position) {

        //get data , set data, handel on click


        //get data
        ModelCourse modelCourse=courseArrayList.get(position);
        String title=modelCourse.getCourse();
        String id=modelCourse.getCid();
        String cname=modelCourse.getCollageName();


        //set data
        holder.courseName.setText(title);



        //handel item click , goto Semester section  also pass courseId and courseTitle

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SemesterUsersActivity.class);
                intent.putExtra("courseId",id);
                intent.putExtra("courseTitle", title);
                intent.putExtra("collageName",cname);
                context.startActivity(intent);
            }
        });



    }



    @Override
    public int getItemCount() {
        //return number of records /list size
        return courseArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filterUsersCourse==null){
           filterUsersCourse=new FilterUsersCourse(filterList,this);
        }
        return filterUsersCourse;
    }


    //view Holder class for row_course.xml

    class HolderCourseAdmin extends RecyclerView.ViewHolder{

        //UI view of row_course.xml
        TextView courseName;


        public HolderCourseAdmin(@NonNull View itemView) {
            super(itemView);
            courseName=binding.courseName;

        }
    }



}