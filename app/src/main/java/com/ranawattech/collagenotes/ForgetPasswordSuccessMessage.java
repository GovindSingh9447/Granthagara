package com.ranawattech.collagenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ranawattech.collagenotes.databinding.ActivityForgetPasswordSuccessMessageBinding;

public class ForgetPasswordSuccessMessage extends AppCompatActivity {

    ActivityForgetPasswordSuccessMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgetPasswordSuccessMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.successBtnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ForgetPasswordSuccessMessage.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}