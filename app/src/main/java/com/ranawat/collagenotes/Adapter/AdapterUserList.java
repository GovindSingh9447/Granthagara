package com.ranawat.collagenotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ranawat.collagenotes.Model.ModelUserList;
import com.ranawat.collagenotes.databinding.RowUserListBinding;

import java.util.ArrayList;

public class AdapterUserList  extends RecyclerView.Adapter<AdapterUserList.HolderUserList>{


    private Context context;
    private ArrayList<ModelUserList> arrayList;


    private RowUserListBinding binding;

    public AdapterUserList(Context context, ArrayList<ModelUserList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HolderUserList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //binding
        binding=RowUserListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderUserList(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUserList holder, int position) {

        //get data
        ModelUserList modelUserList= arrayList.get(position);
        String name=modelUserList.getName();
        String email=modelUserList.getEmail();

        //set data
        holder.namet.setText(name);
        holder.emailt.setText(email);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class  HolderUserList extends RecyclerView.ViewHolder{


        TextView namet, emailt ,lastmsg;

        public HolderUserList(@NonNull View itemView) {
            super(itemView);

            namet=binding.userName;
            emailt=binding.userEmail;
        }
    }
}
