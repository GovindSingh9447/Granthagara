package com.ranawat.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Animes.AnimesActivity;
import com.ranawat.Slider.Model.ModelSlider;
import com.ranawat.Users_Sections.MenuUsersActivity;
import com.ranawat.Users_Sections.UserPolicyActivity;
import com.ranawat.Users_Sections.UserUploadActivity;
import com.ranawat.collagenotes.databinding.ActivityUserDashboardBinding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityUserDashboardBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout content;
    ImageSlider imageSlider;

    //FirebaseAuth
    FirebaseAuth auth;

    static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDashboardBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;
        content = binding.content;
        imageSlider=binding.carousel;

        final List<SlideModel> slideModels=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren()){
                            slideModels.add(new SlideModel(ds.child("url").getValue().toString(), ds.child("title").getValue().toString(), ScaleTypes.FIT));
                        }
                        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        navigationDrawer();

        binding.card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this, AnimesActivity.class));
                finish();
            }
        });




    }

    private void navigationDrawer() {

        //navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = content.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent = new Intent(UserDashboard.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.chatMenu) {
            Intent intent = new Intent(UserDashboard.this, UserPolicyActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.uploadMenu) {
            Intent intent = new Intent(UserDashboard.this, UserUploadActivity.class);
            startActivity(intent);


            return true;
        }
        if (id == R.id.shareMenu) {
            Intent shareintent = new Intent(Intent.ACTION_SEND);
            shareintent.setType("text/plain");
            String sharebody = "Download this Application now to get your all collage notes here:-https://www.mediafire.com/file/s3pnm5hiz8auljc/app-debug.apk/file&hl=en";
            String sharesub = "Collage Notes Apps";

            shareintent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
            shareintent.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(shareintent, "Share Using"));
            return true;
        }

        if (id == R.id.policyMenu) {
            Intent intent = new Intent(UserDashboard.this, UserPolicyActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);


    }


}