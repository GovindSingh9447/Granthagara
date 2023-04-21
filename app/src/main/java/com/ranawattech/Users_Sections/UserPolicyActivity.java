package com.ranawattech.Users_Sections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ranawattech.collagenotes.databinding.ActivityUserPolicyBinding;

public class UserPolicyActivity extends AppCompatActivity {

    private ActivityUserPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}