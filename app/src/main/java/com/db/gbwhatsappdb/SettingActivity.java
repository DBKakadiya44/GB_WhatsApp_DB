package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.db.gbwhatsappdb.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.imageView6.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.menu1.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this, MainActivity.class));
            finish();
        });
        binding.menu2.setOnClickListener(view -> {
//            startActivity(new Intent(SettingActivity.this, ABCD.class));
        });
        binding.menu3.setOnClickListener(view -> {
//            startActivity(new Intent(SettingActivity.this, ABCD.class));
        });
        binding.menu4.setOnClickListener(view -> {
//            startActivity(new Intent(SettingActivity.this, ABCD.class));
        });
        binding.menu5.setOnClickListener(view -> {
//            startActivity(new Intent(SettingActivity.this, ABCD.class));
        });


    }
}