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
import com.ranawat.collagenotes.Model.ModelSemester;
import com.ranawat.Admin_Sections.SubjectAdminActivity;
import com.ranawat.collagenotes.databinding.RowSemesterBinding;
import com.ranawat.collagenotes.filter.FilterSemester;

import java.util.ArrayList;


public class AdapterSemester extends RecyclerView.Adapter<AdapterSemester.HolderSemesterAdmin> implements Filterable {


    //context
    private Context context;

    //arrayList to hold list data of type course
    public ArrayList<ModelSemester> semesterArrayList, filterList;

    //view binding row_semester.xml
    private RowSemesterBinding binding;

    private FilterSemester filter;

    public AdapterSemester(Context context, ArrayList<ModelSemester> semesterArrayList) {
        this.context = context;
        this.semesterArrayList = semesterArrayList;
        this.filterList= semesterArrayList;
    }

    @NonNull
    @Override
    public HolderSemesterAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //bind layout using view binding

        binding=RowSemesterBinding.inflate(LayoutInflater.from(context),parent,false);

        return new HolderSemesterAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSemesterAdmin holder, int position) {

        //get data , set data, handel on click


        //get data
        ModelSemester modelSemester=semesterArrayList.get(position);
        String title=modelSemester.getSemester();
        String id=modelSemester.getCourseId();
        String cname=modelSemester.getCollageName();
        String ccname=modelSemester.getCourse();
        String semesterId=modelSemester.getCid();
        //set data
        holder.semesterName.setText(title);



        //handel on click delete
        holder.deletesemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //confirm delete dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this Semester")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //begin delete
                                Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show();
                                deletesemester(modelSemester, holder);

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
                Intent intent = new Intent(context, SubjectAdminActivity.class);
                intent.putExtra("courseId",id);
                intent.putExtra("semesterTitle", title);
                intent.putExtra("collageName",cname);
                intent.putExtra("courseName",ccname);
                intent.putExtra("semesterId", semesterId);
                context.startActivity(intent);
            }
        });



    }

    private void deletesemester(ModelSemester modelSemester, HolderSemesterAdmin holder) {


        //get id of category to delete
        String cid = modelSemester.getCid();

        //Firebase DB>Collages>collageID
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Semesters");
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
        return semesterArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter =new FilterSemester(filterList, this);

        }

        return filter;
    }

    //view Holder class for row_semester.xml
    class HolderSemesterAdmin extends RecyclerView.ViewHolder{

        //UI view of row_semester.xml
        TextView semesterName;
        ImageButton deletesemester;


        public HolderSemesterAdmin(@NonNull View itemView) {
           super(itemView);

           semesterName=binding.semesterName;
           deletesemester=binding.deletesemester;
       }
   }

}
