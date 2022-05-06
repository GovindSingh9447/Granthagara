package com.ranawat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.collagenotes.Adapter.AdapterUserListMsg;
import com.ranawat.collagenotes.Model.ModelUserList;

import com.ranawat.collagenotes.databinding.FragmentChatAdminBinding;

import java.util.ArrayList;


public class Chat_Admin_Fragment extends Fragment {

    public Chat_Admin_Fragment(){
        //Requried empty public constructor
    }

    FragmentChatAdminBinding binding;
    ArrayList<ModelUserList> arrayList = new ArrayList<>();
    FirebaseDatabase database;
    //firebase Auth
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatAdminBinding.inflate(inflater, container,false);

        AdapterUserListMsg adapterUserListMsg= new AdapterUserListMsg(arrayList ,getContext());
        binding.userChats.setAdapter(adapterUserListMsg);
        database=FirebaseDatabase.getInstance();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        binding.userChats.setLayoutManager(linearLayoutManager);

        firebaseAuth=FirebaseAuth.getInstance();


        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    ModelUserList userList =snapshot1.getValue(ModelUserList.class);
                    userList.getUserId(snapshot1.getKey());
                   /* if(!userList.getUserId().equals(FirebaseAuth.getInstance().getUid())){

                    }*/

                    arrayList.add(userList);

                }
                adapterUserListMsg.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();



    }


}