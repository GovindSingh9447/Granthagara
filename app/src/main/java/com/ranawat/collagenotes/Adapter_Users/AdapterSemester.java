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
import com.ranawat.Admin_Sections.SubjectAdminActivity;
import com.ranawat.Users_Sections.SubjectUsersActivity;
import com.ranawat.collagenotes.Model.ModelSemester;
import com.ranawat.collagenotes.databinding.RowSemesterBinding;
import com.ranawat.collagenotes.databinding.RowSemesterUsersBinding;
import com.ranawat.collagenotes.filter.FilterSemester;
import com.ranawat.collagenotes.filter.FilterUsersSemester;

import java.util.ArrayList;


public class AdapterSemester extends RecyclerView.Adapter<AdapterSemester.HolderSemesterAdmin> implements Filterable{


    //context
    private Context context;

    //arrayList to hold list data of type course
    public ArrayList<ModelSemester> semesterArrayList, filterList;

    //view binding row_semester.xml
    private  RowSemesterUsersBinding binding;

    private FilterUsersSemester filterUsersSemester;



    public AdapterSemester(Context context, ArrayList<ModelSemester> semesterArrayList) {
        this.context = context;
        this.semesterArrayList = semesterArrayList;
        this.filterList=semesterArrayList;

    }

    @NonNull
    @Override
    public HolderSemesterAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //bind layout using view binding

        binding= RowSemesterUsersBinding.inflate(LayoutInflater.from(context),parent,false);

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




        //handel item click , goto Subject section  also pass semesterId and semesterTitle

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubjectUsersActivity.class);
                intent.putExtra("courseId",id);
                intent.putExtra("semesterTitle", title);
                intent.putExtra("collageName",cname);
                intent.putExtra("courseName",ccname);
                intent.putExtra("semesterId", semesterId);
                context.startActivity(intent);
            }
        });



    }




    @Override
    public int getItemCount() {
        return semesterArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filterUsersSemester== null){
            filterUsersSemester= new FilterUsersSemester(filterList,this);
        }
        return filterUsersSemester;
    }


    //view Holder class for row_semester.xml
    class HolderSemesterAdmin extends RecyclerView.ViewHolder{

        //UI view of row_semester.xml
        TextView semesterName;



        public HolderSemesterAdmin(@NonNull View itemView) {
           super(itemView);

           semesterName=binding.semesterName;

       }
   }

}
