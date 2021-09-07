package com.ranawat.Users_Sections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ranawat.collagenotes.databinding.ActivityUserPolicyBinding;

public class UserPolicyActivity extends AppCompatActivity {

    private ActivityUserPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}