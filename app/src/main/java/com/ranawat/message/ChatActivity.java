package com.ranawat.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.collagenotes.Adapter.ChatAdapter;
import com.ranawat.collagenotes.Model.ModelMessage;
import com.ranawat.collagenotes.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    String name,email;
    private FirebaseAuth auth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent=getIntent();
        name=intent.getStringExtra("names");
        email=intent.getStringExtra("emails");
        String senderId=auth.getUid();
        String reciverId=intent.getStringExtra("uid");



        binding.username.setText(name);
        binding.email.setText(email);

        final ArrayList<ModelMessage> modelMessages =new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(modelMessages,this,reciverId);
        binding.chatRecycleView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.chatRecycleView.setLayoutManager(linearLayoutManager);

        final String senderRoom =senderId + reciverId;
        final String reciverRoom= reciverId + senderId;


        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        modelMessages.clear();
                        for(DataSnapshot snapshot1 :snapshot.getChildren()){
                            ModelMessage model=snapshot1.getValue(ModelMessage.class);
                            model.setMessageId(snapshot1.getKey());
                            modelMessages.add(model);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message=binding.typeMsg.getText().toString();
                final ModelMessage modelMessage= new ModelMessage(senderId, message);
                modelMessage.setTimestamp(new Date().getTime());
                binding.typeMsg.setText("");


                database.getReference().child("chats")
                        .child(senderRoom)
                        .push().setValue(modelMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(reciverRoom)
                                .push()
                                .setValue(modelMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {



                            }
                        });
                    }
                });




            }
        });



        


    }
}