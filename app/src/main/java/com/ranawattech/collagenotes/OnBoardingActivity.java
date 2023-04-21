package com.ranawattech.collagenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranawattech.collagenotes.Adapter_slider.SliderAdapter;


public class OnBoardingActivity extends AppCompatActivity {

    //view Binding
    private com.ranawattech.collagenotes.databinding.ActivityOnBoardingBinding binding;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    LinearLayout dotsLayout;
    ViewPager viewPager;
    Button getstarted;
    Animation animation;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.ranawattech.collagenotes.databinding.ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager=binding.slider;
        dotsLayout=binding.dot;
        getstarted= binding.getstarted;


        //handel login click
       getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),GetStartedActivity.class);

                Pair[] pairs=new Pair[1];
                pairs[0]=new Pair<View,String>(binding.getstarted,"transition_getstated");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(OnBoardingActivity.this,pairs);

                startActivity(intent,options.toBundle());


            }
        });

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);




      /*  binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, DashboardUserActivity.class));

            }
        });*/
    }

    public void skip(View view){
        startActivity(new Intent(getApplicationContext(),GetStartedActivity.class));
        finish();

    }
    public void next(View view){
        viewPager.setCurrentItem(currentPosition+1);


    }
    private void addDots(int postion){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0){
            dots[postion].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener changeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            currentPosition=position;
            if(position ==0){
                getstarted.setVisibility(View.INVISIBLE);

            }else if(position==1){
                getstarted.setVisibility(View.INVISIBLE);

            }else if(position==2){
                animation= AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.bottom_anim);
                getstarted.setAnimation(animation);
                getstarted.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}