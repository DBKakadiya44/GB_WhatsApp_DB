package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
import com.db.gbwhatsappdb.Categories.Adapters.CapListAdapter;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityCaptionListBinding;

public class CaptionListActivity extends AppCompatActivity {
    ActivityCaptionListBinding binding;
    int pos=0;
    String[] datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptionListBinding.inflate(getLayoutInflater());
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

        pos = getIntent().getIntExtra("pos",0);

        if(pos==0){datalist = CaptionActivity.str1;}
        if(pos==1){datalist = CaptionActivity.str2;}
        if(pos==2){datalist = CaptionActivity.str3;}
        if(pos==3){datalist = CaptionActivity.str4;}
        if(pos==4){datalist = CaptionActivity.str5;}
        if(pos==5){datalist = CaptionActivity.str6;}
        if(pos==6){datalist = CaptionActivity.str7;}
        if(pos==7){datalist = CaptionActivity.str8;}
        if(pos==8){datalist = CaptionActivity.str9;}
        if(pos==9){datalist = CaptionActivity.str10;}
        if(pos==10){datalist = CaptionActivity.str11;}
        if(pos==11){datalist = CaptionActivity.str12;}
        if(pos==12){datalist = CaptionActivity.str13;}
        if(pos==13){datalist = CaptionActivity.str14;}
        if(pos==14){datalist = CaptionActivity.str15;}
        if(pos==15){datalist = CaptionActivity.str17;}
        if(pos==16){datalist = CaptionActivity.str18;}
        if(pos==17){datalist = CaptionActivity.str19;}
        if(pos==18){datalist = CaptionActivity.str20;}
        if(pos==19){datalist = CaptionActivity.str21;}

        CapListAdapter adapter = new CapListAdapter(CaptionListActivity.this , datalist);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.recyclercaplist.setLayoutManager(manager);
        binding.recyclercaplist.setAdapter(adapter);

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