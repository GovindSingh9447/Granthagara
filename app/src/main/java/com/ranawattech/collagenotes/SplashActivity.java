package com.ranawattech.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawattech.collagenotes.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    //firebase Auth
    FirebaseAuth auth;
    SharedPreferences onBoardingScreen;
    ActivitySplashBinding binding;

    Animation topAnim,bottomAnim,sideAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //init firebase Auth
        auth =FirebaseAuth.getInstance();

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        sideAnim=AnimationUtils.loadAnimation(this,R.anim.side_anim);

        binding.compName.setAnimation(bottomAnim);
        binding.Logo.setAnimation(topAnim);
        binding.sologan.setAnimation(bottomAnim);

        

        //Start main Screen after 2 sec

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen=getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime=onBoardingScreen.getBoolean("firstTime", true);

                if(isFirstTime){
                    SharedPreferences.Editor editor=onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                    finish();
                }
                else{
                    checkUser();
                }

            }
        }, 2000);
    }

    private void checkUser() {
        //get current user, if login
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){

            Toast.makeText(this, "Not logined", Toast.LENGTH_SHORT).show();
            //user not logged in
            //start main screen
            startActivity(new Intent(SplashActivity.this, GetStartedActivity.class));
            finish();
        }
        else
        {
            String users =auth.getUid();
            //user loggin check user type same as done in login screen
            startActivity(new Intent(SplashActivity.this,UserDashboard.class));
            finish();



            //check in db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(user.getUid()).child("phoneno")
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