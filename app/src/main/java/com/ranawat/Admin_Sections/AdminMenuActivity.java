package com.ranawat.Admin_Sections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ranawat.collagenotes.DashboardUserActivity;
import com.ranawat.collagenotes.databinding.ActivityAdminMenuBinding;

public class AdminMenuActivity extends AppCompatActivity {

    private ActivityAdminMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.adUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMenuActivity.this, UserUploadAdminActivity.class);
                startActivity(intent);
            }
        });

        //Send Message
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent=new Intent(AdminMenuActivity.this, UserSentMessagesActivity.class);
                Intent intent=new Intent(AdminMenuActivity.this, Admin_Chat_Users_Activity.class);
                startActivity(intent);

            }
        });

        binding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMenuActivity.this, DashboardUserActivity.class);
                startActivity(intent);
            }
        });

        binding.userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMenuActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });

        binding.adshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent shareintent=new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                String sharebody="Download this Application now to get your all collage notes here:-https://www.mediafire.com/file/s3pnm5hiz8auljc/app-debug.apk/file&hl=en";
                String sharesub="Collage Notes Apps";

                shareintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                shareintent.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(shareintent,"Share Using"));
            }
        });
    }
}