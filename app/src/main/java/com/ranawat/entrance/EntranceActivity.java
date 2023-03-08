package com.ranawat.entrance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ranawat.collagenotes.R;
import com.ranawat.collagenotes.databinding.ActivityEntranceBinding;

public class EntranceActivity extends AppCompatActivity {

    ActivityEntranceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEntranceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





    }
}