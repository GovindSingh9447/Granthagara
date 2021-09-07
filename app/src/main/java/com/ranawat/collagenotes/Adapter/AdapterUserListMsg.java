package com.ranawat.collagenotes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ranawat.Fragments.Chat_Admin_Fragment;
import com.ranawat.collagenotes.Model.ModelSemester;
import com.ranawat.collagenotes.Model.ModelUserList;
import com.ranawat.collagenotes.databinding.RowUserListBinding;
import com.ranawat.collagenotes.databinding.RowUserMsgBinding;

import java.util.ArrayList;

public class AdapterUserListMsg extends RecyclerView.Adapter<AdapterUserListMsg.HolderUserList>{


    private Context context;
    private ArrayList<ModelUserList> arrayList;


    private @NonNull RowUserMsgBinding binding;

    public AdapterUserListMsg(Context context, ArrayList<ModelUserList> arrayList) {

    }

    public AdapterUserListMsg(ArrayList<ModelUserList> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public HolderUserList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //binding
        binding= RowUserMsgBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderUserList(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUserList holder, int position) {

        //get data
        ModelUserList modelUserList= arrayList.get(position);
        String name=modelUserList.getName();
        String lastmsg=modelUserList.getLastmsg();
        String userId =modelUserList.getUserId();


        //set data
        holder.namet.setText(name);
        holder.lastmsg.setText(lastmsg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Chat_Admin_Fragment.class);
                intent.putExtra("uid",userId);
                intent.putExtra("name" , name);
                intent.putExtra("lstmsg",lastmsg);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class  HolderUserList extends RecyclerView.ViewHolder{


        TextView namet ,lastmsg;

        public HolderUserList(@NonNull View itemView) {
            super(itemView);

            namet=binding.userName;
            lastmsg=binding.lastMsg;
        }
    }
}
