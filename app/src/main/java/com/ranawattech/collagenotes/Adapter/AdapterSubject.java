package com.ranawattech.collagenotes.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ranawattech.collagenotes.Model.ModelSubject;
import com.ranawattech.Admin_Sections.NotesAdminActivity;
import com.ranawattech.collagenotes.databinding.RowSubjectBinding;

import java.util.ArrayList;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.HolderSubjectAdmin>  {

    //context
    private Context context;

    //arrayList to hold List of subjects
    private ArrayList<ModelSubject> subjectArrayList;

    //binding
    private RowSubjectBinding binding;

    public AdapterSubject(Context context, ArrayList<ModelSubject> subjectArrayList) {
        this.context = context;
        this.subjectArrayList = subjectArrayList;
    }

    @NonNull
    @Override
    public HolderSubjectAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding= RowSubjectBinding.inflate(LayoutInflater.from(context),parent ,false);
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




        //handel on click delete
        holder.deleteSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //confirm delete dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this Subject")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //begin delete
                                Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show();
                                deletesubject(modelSubject, holder);

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



        //handel item click , goto Subject section  also pass semesterId and semesterTitle

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesAdminActivity.class);
                intent.putExtra("courseId",id);
                intent.putExtra("subjectTitle", title);
                intent.putExtra("subjectId", subId);
                context.startActivity(intent);
            }
        });





    }

    private void deletesubject(ModelSubject modelSubject, HolderSubjectAdmin holder) {


        //get id of category to delete
        String cid = modelSubject.getCid();

        //Firebase DB>Collages>collageID
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Subjects");
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
        return subjectArrayList.size();
    }

    //view Holder for row_subject.xml
    class HolderSubjectAdmin extends RecyclerView.ViewHolder{

        //ui view of row_subject.xml
        TextView subjectName;
        ImageButton deleteSubject;


        public HolderSubjectAdmin(@NonNull View itemView) {
            super(itemView);

            subjectName=binding.subjectName;
            deleteSubject=binding.deletesubject;
        }
    }


}