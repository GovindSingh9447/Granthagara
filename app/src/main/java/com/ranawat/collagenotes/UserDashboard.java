package com.ranawat.collagenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ranawat.Animes.AnimesActivity;
import com.ranawat.Animes.AnimesAdapter.AnimesAdapter;
import com.ranawat.Animes.AnimesModel.AnimesModel;
import com.ranawat.Hindi_Upnayas.Adapter.HinUpnAdapter;
import com.ranawat.Hindi_Upnayas.HindiUpnayasActivity;
import com.ranawat.Hindi_Upnayas.Model.HinUpnModel;
import com.ranawat.Interview.InterviewActivity;
import com.ranawat.Interview.InterviewAdapter.InterviewAdapter;
import com.ranawat.Interview.InterviewModel.InterviewModel;
import com.ranawat.Interview.UploadInterviewActivity;
import com.ranawat.School.ClassActivity;
import com.ranawat.School.SchoolAdapter.ClassAdapter;
import com.ranawat.School.SchoolModel.ClassModel;
import com.ranawat.School.UploadSchoolDataActivity;
import com.ranawat.Slider.Model.ModelSlider;
import com.ranawat.Users_Sections.MenuUsersActivity;
import com.ranawat.Users_Sections.UserPolicyActivity;
import com.ranawat.Users_Sections.UserUploadActivity;
import com.ranawat.collagenotes.Model.ModelCollage;
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

    //School section
    private ArrayList<ClassModel> classModelArrayList;
    private ClassAdapter classAdapter;
    private RecyclerView recyclerView;

    //Anime Section Or Manga
    private ArrayList<AnimesModel> animesModelArrayList;
    private AnimesAdapter animesAdapter;
    private RecyclerView recycleAnime;

    //College section or University
    private ArrayList<ModelCollage> collageArrayList;
    private com.ranawat.collagenotes.Adapter_Users.AdapterCollage adapterCollage;


    //Interview Section
    private ArrayList<InterviewModel> interviewModelArrayList;
    private InterviewAdapter interviewAdapter;

    //HindiUpanyas Section
    private  ArrayList<HinUpnModel> hinUpnModelArrayList;
    private HinUpnAdapter hinUpnAdapter;


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

        binding.interviewcard.setOnClickListener(view -> {
            Intent intent=new Intent(UserDashboard.this, InterviewActivity.class);
            startActivity(intent);
            finish();

        });


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

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, ProfileActivity.class));
                finish(); 
            }
        });

        //SCHOOL

        binding.uploadSchhol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this, UploadSchoolDataActivity.class));
                finish();
            }
        });
        binding.classAll.setOnClickListener(v->{
            Intent intent=new Intent(UserDashboard.this, ClassActivity.class);
            startActivity(intent);
            finish();
        });
        loadClass();


        //Anime section or Manga
        recycleAnime=binding.manga;
        binding.mangaAll.setOnClickListener(v->{
            Intent intent=new Intent(UserDashboard.this,AnimesActivity.class);
            startActivity(intent);
            finish();
        });

        loadAnimes();

        //College section or University
        loadCollages();


        //Interview Section
        binding.interviewAll.setOnClickListener(v->{
            Intent intent =new Intent(this, InterviewActivity.class);
            startActivity(intent);
            finish();
        });

        loadInterView();

        //UploadInterview
       binding.uploadInterview.setOnClickListener(v->{
           Intent intent=new Intent(this,UploadInterviewActivity.class);
           startActivity(intent);
           finish();
       });



        //Hind_Upanyas
        binding.hindiUpnViewAll.setOnClickListener(view -> {
            Intent intent =new Intent(UserDashboard.this, HindiUpnayasActivity.class);
            startActivity(intent);
            finish();
        });

        loadHindUpn();






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
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(UserDashboard.this);
            alertDialog.setTitle("Exit from Granthagra");
            alertDialog.setMessage("Do you really want to exit?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
           // super.onBackPressed();
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
            String sharebody = "Download this Application now to get your all Granthagra here:-https://www.mediafire.com/file/s3pnm5hiz8auljc/app-debug.apk/file&hl=en";
            String sharesub = "Granthagra Apps";

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


    //SCHOOL
    private void loadClass() {
        classModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("School");
        reference.limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);



                    ClassModel classModel=ds.getValue(ClassModel.class);


                    classModelArrayList.add(classModel);
                }
                classAdapter = new ClassAdapter(UserDashboard.this, classModelArrayList);

                //set adapter to recycle
                binding.classview.setAdapter(classAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    //Anime Section Or Manga
    private void loadAnimes() {
        //init arrayList

        animesModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Animes");
        reference.limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animesModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);


                    AnimesModel animesModel = ds.getValue(AnimesModel.class);


                    animesModelArrayList.add(animesModel);
                }
                animesAdapter = new AnimesAdapter(UserDashboard.this, animesModelArrayList);

                recycleAnime.setAdapter(animesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Interview Section

    private void loadInterView() {
        interviewModelArrayList=new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("InterviewSection");
        reference.limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                interviewModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);
                    InterviewModel interviewModel=ds.getValue(InterviewModel.class);


                    interviewModelArrayList.add(interviewModel);
                }
                interviewAdapter = new InterviewAdapter(UserDashboard.this, interviewModelArrayList);


                binding.interview.setAdapter(interviewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //College Or University Section
    private void loadCollages() {

        //init arraylist
        collageArrayList = new ArrayList<>();


        //get all collages from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Collages");
        reference.limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //cle ar arraylist before adding data into it
                collageArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {

                    //get data

                    ModelCollage modelCollage = ds.getValue(ModelCollage.class);

                    //add to arraylist
                    collageArrayList.add(modelCollage);

                }
                //setup adapter
                adapterCollage = new com.ranawat.collagenotes.Adapter_Users.AdapterCollage(UserDashboard.this, collageArrayList);


                //set adapter to recycle
                binding.recycleViewCollege.setAdapter(adapterCollage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //Hindi upanyas

    private void loadHindUpn() {

        //init arrayList

        hinUpnModelArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hindi_Upanyas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hinUpnModelArrayList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    // String value=String.valueOf(snapshot.child("animeName").getValue());
                    // System.out.println("NameAnime"+value);


                    HinUpnModel hinUpnModel = ds.getValue(HinUpnModel.class);


                    hinUpnModelArrayList.add(hinUpnModel);
                }
                hinUpnAdapter = new HinUpnAdapter(UserDashboard.this, hinUpnModelArrayList);

                binding.hindiUpnRecycle.setAdapter(hinUpnAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }







}