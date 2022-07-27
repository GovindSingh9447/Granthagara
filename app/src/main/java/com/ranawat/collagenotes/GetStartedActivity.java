package com.ranawat.collagenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;


public class GetStartedActivity extends AppCompatActivity {
    com.ranawat.collagenotes.databinding.ActivityGetStartedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.ranawat.collagenotes.databinding.ActivityGetStartedBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);

                Pair[] pairs=new Pair[1];
                pairs[0]=new Pair<View,String>(binding.login,"transition_login");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GetStartedActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                    finish();
                } else {
                    startActivity(intent);
                    finish();
                }


            }
        });
        binding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);

                Pair[] pairs=new Pair[1];
                pairs[0]=new Pair<View,String>(binding.login,"transition_registration");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(GetStartedActivity.this,pairs);

                startActivity(intent,options.toBundle());
                finish();

            }
        });



    }
}