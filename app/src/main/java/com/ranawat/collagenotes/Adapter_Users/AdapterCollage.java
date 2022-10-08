package com.ranawat.collagenotes.Adapter_Users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ranawat.Users_Sections.CourseUsersActivity;
import com.ranawat.collagenotes.Model.ModelCollage;
import com.ranawat.collagenotes.databinding.MostViewedCardDesignBinding;
import com.ranawat.collagenotes.databinding.RowCollageBinding;
import com.ranawat.collagenotes.databinding.RowCollageUsersBinding;
import com.ranawat.collagenotes.filter.FilterUsersCollage;

import java.util.ArrayList;
import java.util.Locale;


public class AdapterCollage extends RecyclerView.Adapter<AdapterCollage.HolderCollage> implements Filterable {

    private Context context;
    public ArrayList<ModelCollage> collageArrayList ,filterList;


    //view binding
    private MostViewedCardDesignBinding binding;
    private FilterUsersCollage filterUsersCollage;



    public AdapterCollage(Context context, ArrayList<ModelCollage> collageArrayList) {
        this.context = context;
        this.collageArrayList = collageArrayList;
        this.filterList=collageArrayList;

    }

    @NonNull
    @Override
    public HolderCollage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //binding  row_Collage.xml
       // binding=RowCollageUsersBinding.inflate(LayoutInflater.from(context),parent,false);
        binding= MostViewedCardDesignBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderCollage(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCollage holder, int position) {

        //get data
        ModelCollage model =collageArrayList.get(position);
        String cid=model.getCid();
        String collage=model.getCollage();
        String uid=model.getUid();
        String img=model.getImg();
        long timestamp=model.getTimestamp();



        //set data
        holder.collagename.setText(collage);
        Glide.with(context).load(img).into(holder.most_Viewed);




        //handel item click , goto to course section from collage section
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, CourseUsersActivity.class);
                intent.putExtra("CollageId" ,cid);
                intent.putExtra("CollageTitle" ,collage);
                context.startActivity(intent);
            }
        });


    }







    @Override
    public int getItemCount() {
        return collageArrayList.size();
    }

    @Override
    public Filter getFilter() {

        if (filterUsersCollage== null){
            filterUsersCollage=new FilterUsersCollage(filterList,this);
        }
        return filterUsersCollage;
    }







    /*-------------------View holder class to hold UI views for row_Collage_Users.xml---------------*/

    class HolderCollage extends RecyclerView.ViewHolder{

        //UI view of row_Collage_Users.xml
        TextView collagename;
        ImageView most_Viewed;


        public HolderCollage(@NonNull View itemView) {
            super(itemView);

            collagename=binding.collageName;
            collagename.setSelected(true);
            most_Viewed=binding.mostViewed;

        }
    }
}
