package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivitySavedStatusBinding;
import com.db.gbwhatsappdb.wa.Fragments.SavedFilesFragment;
import com.db.gbwhatsappdb.wa.Utils.Common;

public class SavedStatusActivity extends AppCompatActivity {
    ActivitySavedStatusBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);

        LinearLayout banner = findViewById(R.id.bannerLayout);
        BannerAD bannerAd = new BannerAD(this, banner);
        bannerAd.loadBannerAd();

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        if (Common.APP_DIR == null || Common.APP_DIR.isEmpty()) {
            Common.APP_DIR = getExternalFilesDir("StatusDownloader").getPath();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framesave, new SavedFilesFragment());
        fragmentTransaction.commit();

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