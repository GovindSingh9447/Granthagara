package com.ranawat.collagenotes.Adapter_slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.ranawat.collagenotes.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }


//    int images[]={
//            R.drawable.slider_img_1,
//            R.drawable.slider_img2,
//            R.drawable.slider_img3
//
//    };

    int images[]={
            R.raw.anim1,
            R.raw.anim2,
            R.raw.anim3,
            R.raw.anim4


    };

    int headline[]={
            R.string.slider_heading1,
            R.string.slider_heading2,
            R.string.slider_heading3
    };

    int descs[]={
            R.string.slider_desc1,
            R.string.slider_desc2,
            R.string.slider_desc3
    };

    @Override
    public int getCount() {
        return headline.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slides_layout,container,false);

        LottieAnimationView animationView = view.findViewById(R.id.slider_image);
       // TextView heading=view.findViewById(R.id.slider_headline);
      //  TextView desc=view.findViewById(R.id.slider_desc);

        animationView.setAnimation(images[position]);
       // heading.setText(headline[position]);
       // desc.setText(descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
