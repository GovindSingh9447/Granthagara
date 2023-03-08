package com.ranawat.entrance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ranawat.collagenotes.R;
import com.ranawat.collagenotes.databinding.ActivityEntranceAtivityBinding;

public class EntranceAtivity extends AppCompatActivity {

    ActivityEntranceAtivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEntranceAtivityBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_entrance_ativity);





    }
}