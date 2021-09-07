package com.ranawat.collagenotes.Adapter;


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
import com.ranawat.collagenotes.Model.ModelCourse;
import com.ranawat.Admin_Sections.SemesterAdminActivity;
import com.ranawat.collagenotes.databinding.RowCourseBinding;
import com.ranawat.collagenotes.filter.FilterCourse;

import java.util.ArrayList;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.HolderCourseAdmin> implements Filterable {

    //context
    private Context context;

    //arrayList to hold list data of type course
    public ArrayList<ModelCourse> courseArrayList,filterList;

    //view binding row_course.xml
    private RowCourseBinding binding;

    private FilterCourse filter;



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
        binding= RowCourseBinding.inflate(LayoutInflater.from(context),parent,false);

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


        //handel on click delete
        holder.deletecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //confirm delete dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this collage")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //begin delete
                                Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show();
                                deletecourse(modelCourse, holder);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });

        //handel item click , goto Semester section  also pass courseId and courseTitle

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SemesterAdminActivity.class);
                intent.putExtra("courseId",id);
                intent.putExtra("courseTitle", title);
                intent.putExtra("collageName",cname);
                context.startActivity(intent);
            }
        });



    }

    private void deletecourse(ModelCourse modelCourse, HolderCourseAdmin holder) {
        //get id of category to delete
        String cid = modelCourse.getCid();

        //Firebase DB>Collages>collageID
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Courses");
        reference.child(cid)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //delete successful
                        Toast.makeText(context, "Successfully delete...", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failer
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

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
        if (filter==null){
            filter = new FilterCourse(filterList,this);
        }
        return filter;
    }


    //view Holder class for row_course.xml

    class HolderCourseAdmin extends RecyclerView.ViewHolder{

        //UI view of row_course.xml
        TextView courseName;
        ImageButton deletecourse;

        public HolderCourseAdmin(@NonNull View itemView) {
            super(itemView);
            courseName=binding.courseName;
            deletecourse=binding.deletecourse;
        }
    }



}