package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
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

        Native aNative = new Native(this);
        aNative.ShowNative(this, findViewById(R.id.native_container),1);
        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);

        binding.imageView6.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.menu1.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    finish();
                }
                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    finish();
                }
            });
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
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);
        helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
            @Override
            public void onAdLoadFailed() {
                finish();
            }

            @Override
            public void onInterstitialDismissed() {
                finish();
            }
        });
    }
}