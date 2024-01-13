package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.db.gbwhatsappdb.Categories.CaptionActivity;
import com.db.gbwhatsappdb.Categories.DirectMessageActivity;
import com.db.gbwhatsappdb.Categories.FlipTextActivity;
import com.db.gbwhatsappdb.Categories.SavedStatusActivity;
import com.db.gbwhatsappdb.Categories.TextArtActivity;
import com.db.gbwhatsappdb.Categories.TextRepeaterActivity;
import com.db.gbwhatsappdb.Categories.TextStyleActivity;
import com.db.gbwhatsappdb.Categories.WaWebActivity;
import com.db.gbwhatsappdb.WABusiness.WABusinessActivity;
import com.db.gbwhatsappdb.databinding.ActivityMainBinding;
import com.db.gbwhatsappdb.wa.WhatsAppActivity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.setting.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this , SettingActivity.class));
        });

        binding.menu1.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WhatsAppActivity.class));
        });
        binding.menu2.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WABusinessActivity.class));
        });
        binding.menu3.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TextRepeaterActivity.class));
        });
        binding.menu4.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DirectMessageActivity.class));
        });
        binding.menu5.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WaWebActivity.class));
        });
        binding.menu6.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, FlipTextActivity.class));
        });
        binding.menu7.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TextStyleActivity.class));
        });
        binding.menu8.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TextArtActivity.class));
        });
        binding.menu9.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CaptionActivity.class));
        });
        binding.menu10.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SavedStatusActivity.class));
        });


    }
}