package com.ranawat.Admin_Sections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ranawat.collagenotes.databinding.ActivityAdminChatDetailsBinding;

public class AdminChatDetailsActivity extends AppCompatActivity {

    private ActivityAdminChatDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}