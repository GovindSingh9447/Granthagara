package com.ranawattech.collagenotes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ranawattech.collagenotes.Model.ModelUserList;
import com.ranawattech.collagenotes.databinding.RowUserListBinding;
import com.ranawattech.collagenotes.databinding.RowUserMsgBinding;
import com.ranawattech.message.ChatActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUserListMsg extends RecyclerView.Adapter<AdapterUserListMsg.HolderUserList>{


    private Context context;
    private ArrayList<ModelUserList> arrayList;


    private @NonNull RowUserMsgBinding binding;


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
        String email=modelUserList.getEmail();


        /*if(!modelUserList.getProfilePic().isEmpty()){
            Picasso.get().load(modelUserList.getProfilePic()).into(holder.profilePic);
        }*/

        if(modelUserList.getUnSeenMsg() == 0){
            holder.unseenMsg.setVisibility(View.GONE);
        }else{
            holder.unseenMsg.setVisibility(View.VISIBLE);
        }

        //set data
        holder.namet.setText(name);
        holder.lastmsg.setText(lastmsg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("uid",userId);
                intent.putExtra("names" , name);
                intent.putExtra("lstmsg",lastmsg);
                intent.putExtra("emails",email);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class  HolderUserList extends RecyclerView.ViewHolder{


        TextView namet ,lastmsg, unseenMsg;
        CircleImageView profilePic;
        LinearLayout rootLayout;

        public HolderUserList(@NonNull View itemView) {
            super(itemView);

            namet=binding.userName;
            lastmsg=binding.lastMsg;
            profilePic=binding.profilePic;
            unseenMsg=binding.unseenMsg;
            rootLayout=binding.rootLayout;
        }
    }
}
