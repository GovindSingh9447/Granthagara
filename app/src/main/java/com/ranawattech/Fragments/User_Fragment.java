package com.ranawattech.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.collagenotes.Adapter.AdapterUserList;
import com.ranawattech.collagenotes.Model.ModelUserList;
import com.ranawattech.collagenotes.R;
import com.ranawattech.collagenotes.databinding.FragmentUserBinding;

import java.util.ArrayList;


public class User_Fragment extends Fragment {

    public User_Fragment(){

    }

    FragmentUserBinding binding;
    ArrayList<ModelUserList> arrayList=new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentUserBinding.inflate(inflater, container,false);

        AdapterUserList adapterUserList=new AdapterUserList(getContext(), arrayList);
        binding.userList.setAdapter(adapterUserList);
        database=FirebaseDatabase.getInstance();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        binding.userList.setLayoutManager(linearLayoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    ModelUserList userList =snapshot1.getValue(ModelUserList.class);
                    userList.getUserId(snapshot1.getKey());
                    arrayList.add(userList);
                }
                adapterUserList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}