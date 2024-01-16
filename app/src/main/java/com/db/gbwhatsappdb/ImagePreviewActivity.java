package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.databinding.ActivityImagePreviewBinding;
import com.db.gbwhatsappdb.wa.Adapter.ImageAdapter;
import com.db.gbwhatsappdb.wa.Models.Status;
import com.db.gbwhatsappdb.wa.Utils.Common;

public class ImagePreviewActivity extends AppCompatActivity {
    ActivityImagePreviewBinding binding;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImagePreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        pos = getIntent().getIntExtra("pos",0);

        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);

        LinearLayout banner = findViewById(R.id.bannerLayout);
        BannerAD bannerAd = new BannerAD(this, banner);
        bannerAd.loadBannerAd();

        Status status = ImageAdapter.imagesList.get(pos);
        if (status.isApi30()) {
            Glide.with(this).load(status.getDocumentFile().getUri()).into(binding.preImage);
        } else {
            Glide.with(this).load(status.getFile()).into(binding.preImage);
        }

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.download.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    Common.copyFile(status, ImagePreviewActivity.this, binding.relative);
                }
                @Override
                public void onInterstitialDismissed() {
                    Common.copyFile(status, ImagePreviewActivity.this, binding.relative);
                }
            });

        });

        binding.share.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpg");
            if (status.isApi30()) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, status.getDocumentFile().getUri());
            } else {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.getFile().getAbsolutePath()));
            }
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(Intent.createChooser(shareIntent, "Share image"));
                }
                @Override
                public void onInterstitialDismissed() {
                    startActivity(Intent.createChooser(shareIntent, "Share image"));
                }
            });

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