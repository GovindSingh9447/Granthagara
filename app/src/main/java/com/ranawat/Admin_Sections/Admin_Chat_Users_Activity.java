package com.ranawat.Admin_Sections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ranawat.collagenotes.Adapter.AdapterFragments;
import com.ranawat.collagenotes.databinding.ActivityAdminChatUsersBinding;

public class Admin_Chat_Users_Activity extends AppCompatActivity {
    private ActivityAdminChatUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminChatUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.viewpager.setAdapter(new AdapterFragments(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
}