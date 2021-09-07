package com.ranawat.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    //firebase Auth
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        //init firebase Auth
        firebaseAuth =FirebaseAuth.getInstance();
        

        //Start main Screen after 2 sec

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkUser();
            }
        }, 2000);
    }

    private void checkUser() {
        //get current user, if login
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser==null){

            //user not logged in
            //start main screen
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        else
        {
            //user loggin check user type same as done in login screen

            //check in db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            //get user type
                            String userType= ""+snapshot.child("userType").getValue();

                            //check user type
                            if(userType.equals("user")){
                                //this is the simple user open the user Dashboard
                                startActivity(new Intent(SplashActivity.this,DashboardUserActivity.class));
                                finish();

                            }else if(userType.equals("admin")){
                                //this the Admin , Open the Admin Dashboard
                                startActivity(new Intent(SplashActivity.this,DashboardAdminActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

    }
}