package com.ranawattech.collagenotes.Adapter_Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ranawattech.collagenotes.Model.ModelMessages;
import com.ranawattech.collagenotes.databinding.RowMessageBinding;

import java.util.ArrayList;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.HolderMessage>{

    private RowMessageBinding binding;

    private Context context;

    private ArrayList<ModelMessages> arrayList;

    public AdapterMessage(Context context, ArrayList<ModelMessages> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HolderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //bind layout using view binding

        binding= RowMessageBinding.inflate(LayoutInflater.from(context));
        return new HolderMessage(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMessage holder, int position) {

        //get data
        ModelMessages modelMessages= arrayList.get(position);

        String message=modelMessages.getMessage();
        String uid=modelMessages.getUid();
        String senderName=modelMessages.getSendername();



        //set data
        holder.message.setText(message);




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class HolderMessage extends RecyclerView.ViewHolder{

        //UI view of row_semester.xml
        TextView message;

        public HolderMessage(@NonNull View itemView) {
            super(itemView);

            message=binding.messageName;
        }
    }
}
