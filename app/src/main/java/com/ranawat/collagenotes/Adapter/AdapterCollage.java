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
import com.ranawat.Admin_Sections.CourseAdminActivity;
import com.ranawat.collagenotes.filter.FilterCollage;
import com.ranawat.collagenotes.Model.ModelCollage;
import com.ranawat.collagenotes.databinding.RowCollageBinding;

import java.util.ArrayList;


public class AdapterCollage extends RecyclerView.Adapter<AdapterCollage.HolderCollage> implements Filterable {

    private Context context;
    public ArrayList<ModelCollage> collageArrayList ,filterList;


    //view binding
    private RowCollageBinding binding;

    //instance of our filter class
    private FilterCollage filter;

    public AdapterCollage(Context context, ArrayList<ModelCollage> collageArrayList) {
        this.context = context;
        this.collageArrayList = collageArrayList;
        this.filterList = collageArrayList;
    }

    @NonNull
    @Override
    public HolderCollage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //binding  row_Collage.xml
        binding=RowCollageBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderCollage(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCollage holder, int position) {

        //get data
        ModelCollage model =collageArrayList.get(position);
        String cid=model.getCid();
        String collage=model.getCollage();
        String uid=model.getUid();
        long timestamp=model.getTimestamp();


        //set data
        holder.collagename.setText(collage);


        //handel on click delete
        holder.delCol.setOnClickListener(new View.OnClickListener() {
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
                                deletecollage(model, holder);

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

        //handel item click , goto to course section from collage section
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, CourseAdminActivity.class);
                intent.putExtra("CollageId" ,cid);
                intent.putExtra("CollageTitle" ,collage);
                context.startActivity(intent);
            }
        });


    }

    private void deletecollage(ModelCollage model, HolderCollage holder) {

        //get id of category to delete
        String cid = model.getCid();

        //Firebase DB>Collages>collageID
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Collages");
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
        return collageArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter ==null)
        {
            filter = new FilterCollage(filterList, this);

        }
        return filter;
    }



    /*-------------------View holder class to hold UI views for row_Collage.xml---------------*/

    class HolderCollage extends RecyclerView.ViewHolder{

        //UI view ofrow_Collage.xml
        TextView collagename;
        ImageButton delCol;

        public HolderCollage(@NonNull View itemView) {
            super(itemView);

            collagename=binding.collageName;
            delCol=binding.deletecollage;
        }
    }
}
