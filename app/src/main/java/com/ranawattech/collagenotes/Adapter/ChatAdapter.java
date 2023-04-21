package com.ranawattech.collagenotes.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ranawattech.collagenotes.Model.ModelMessage;
import com.ranawattech.collagenotes.R;

import java.util.ArrayList;

public class ChatAdapter extends  RecyclerView.Adapter{

    ArrayList<ModelMessage> modelMessages;
    Context context;
    String reciverId;

    int SENDER_VIEW_TYPE=1;
    int RECIVER_VIEW_TYPE=2;


    public ChatAdapter(ArrayList<ModelMessage> modelMessages, Context context) {
        this.modelMessages = modelMessages;
        this.context = context;

    }

    public ChatAdapter(ArrayList<ModelMessage> modelMessages, Context context, String reciverId) {
        this.modelMessages = modelMessages;
        this.context = context;
        this.reciverId = reciverId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType ==SENDER_VIEW_TYPE)
       {
           View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
           return new SenderViewHolder(view);
       }
       else
       {
           View view= LayoutInflater.from(context).inflate(R.layout.sample_reciver,parent,false);
           return new ReciverviewHolder(view);

       }
    }

    @Override
    public int getItemViewType(int position) {
       if(modelMessages.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
           return SENDER_VIEW_TYPE;
       }
       else
       {
           return RECIVER_VIEW_TYPE;
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ModelMessage modelMessage=modelMessages.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Unsend")
                        .setMessage("Are you sure you want to unsend this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom =FirebaseAuth.getInstance().getUid()+reciverId;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(modelMessage.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                return false;
            }
        });

        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(modelMessage.getMessage());
        }
        else
        {
            ((ReciverviewHolder)holder).reciverMsg.setText(modelMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return modelMessages.size();
    }

    public class ReciverviewHolder extends RecyclerView.ViewHolder{

        TextView reciverMsg, reciverTime;
        public ReciverviewHolder(@NonNull View itemView) {
            super(itemView);

            reciverMsg=itemView.findViewById(R.id.reciverText);
            reciverTime=itemView.findViewById(R.id.recivertime);


        }
    }

    public  class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }
}
