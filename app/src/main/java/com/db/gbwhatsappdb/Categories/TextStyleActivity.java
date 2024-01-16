package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
import com.db.gbwhatsappdb.Categories.Adapters.TextStyleAdapter;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityTextStyleBinding;

public class TextStyleActivity extends AppCompatActivity {
    ActivityTextStyleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextStyleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        Native aNative = new Native(this);
        aNative.ShowNative(this, findViewById(R.id.native_container),1);

        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);

        LinearLayout banner = findViewById(R.id.bannerLayout);
        BannerAD bannerAd = new BannerAD(this, banner);
        bannerAd.loadBannerAd();

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.imageView4.setOnClickListener(view -> {
            binding.etText.setText("");
            binding.recyclerstyle.setVisibility(View.INVISIBLE);
        });

        binding.btnohk.setOnClickListener(view -> {

            binding.recyclerstyle.setVisibility(View.VISIBLE);

            String stringExtra = binding.etText.getText().toString();


            TextStyleAdapter GBAdapter_Auto_generate = new TextStyleAdapter(this, new String[]{".•♥•.¸¸.•♥•" + stringExtra + "•♥•.¸¸.•♥•.", "♥♥" + stringExtra + "♥♥", "★彡" + stringExtra + "彡☆", "❋. _ " + stringExtra + "_ .❋", "ღ。" + stringExtra + "★ღ", "♥•*" + stringExtra + "*•♥", "*..." + stringExtra + "...*", "♥" + stringExtra + "♥", "L●L" + stringExtra + "L●L", "●♥" + stringExtra + "♥●", "_ •" + stringExtra + "• _", "✪☻" + stringExtra + "✪☻", "✿¨¯`✿" + stringExtra + "✿¨¯`✿", "»♥" + stringExtra + "»♥", "░" + stringExtra + "░", "▒░░" + stringExtra + "░▒▓", "*´♥`*" + stringExtra + "*´♥`*", "●́‿●" + stringExtra + "●́‿●", "♬♪♫" + stringExtra + "♬♪♫", "◦'⌣'◦" + stringExtra + "◦'⌣'◦", "ξ◕◡◕ξ" + stringExtra + "ξ◕◡◕ξ", "✿" + stringExtra + "✿", "❆❆" + stringExtra + "❆❆", "╠♥╣" + stringExtra + "╠♥╣", "►" + stringExtra + "◄", ":̲̅:" + stringExtra + ":̲̅:", "︻┳═一" + stringExtra, "꧁༒☬" + stringExtra + "☬༒꧂", "꧁༺" + stringExtra + "༻꧂", "꧁༒♛" + stringExtra + "♛༒꧂", "◥꧁ད" + stringExtra + "ཌ꧂◤", "亗『" + stringExtra + "』亗", "༒☬〖ℳℜ〗" + stringExtra + "73☬༒"}, "");
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            binding.recyclerstyle.setLayoutManager(manager);

            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    binding.recyclerstyle.setAdapter(GBAdapter_Auto_generate);
                }
                @Override
                public void onInterstitialDismissed() {
                    binding.recyclerstyle.setAdapter(GBAdapter_Auto_generate);
                }
            });

            GBAdapter_Auto_generate.notifyDataSetChanged();



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