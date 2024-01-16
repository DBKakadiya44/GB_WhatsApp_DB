package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
import com.db.gbwhatsappdb.Categories.Adapters.ArtAdapter;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityTextArtBinding;

public class TextArtActivity extends AppCompatActivity {
    ActivityTextArtBinding binding;
    RecyclerView rv_Stylish_art;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextArtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        Native aNative = new Native(this);
        aNative.ShowNative(this, findViewById(R.id.native_container),1);

        LinearLayout banner = findViewById(R.id.bannerLayout);
        BannerAD bannerAd = new BannerAD(this, banner);
        bannerAd.loadBannerAd();

        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        ArtAdapter adapter = new ArtAdapter(this, new String[]{".•♥•.¸¸.•♥••♥•.¸¸.•♥•.", "❋. _ _ .❋", "♥•**•♥", "*......*", "L●L", "✪☻✪☻", "✿¨¯`✿", "»♥", "░░", "▒░░░▒▓", "*´♥`*", "●́‿●", "♬♪♫", "◦'⌣'◦", "ξ◕◡◕ξ", "❆❆", "╠♥╣", "︻┳═一", "꧁༒☬☬༒꧂", "꧁༺༻꧂", "꧁༒♛♛༒꧂", "◥꧁དཌ꧂◤", "亗『』亗", "༒☬〖ℳℜ〗"}, "");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.rvStylishArt.setLayoutManager(manager);
        binding.rvStylishArt.setAdapter(adapter);
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