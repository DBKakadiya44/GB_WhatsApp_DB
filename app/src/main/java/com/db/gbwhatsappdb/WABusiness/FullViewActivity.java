package com.db.gbwhatsappdb.WABusiness;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.WABusiness.adapters.ShowImagesAdapter;
import com.db.gbwhatsappdb.WABusiness.util_items.Utils;
import com.db.gbwhatsappdb.databinding.ActivityFullViewBinding;

import java.io.File;
import java.util.ArrayList;

public class FullViewActivity extends AppCompatActivity {
    private ActivityFullViewBinding binding;
    private FullViewActivity activity;
    private ArrayList<File> fileArrayList;
    private int Position = 0;
    ShowImagesAdapter showImagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_view);
        activity = this;

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        LinearLayout banner = findViewById(R.id.bannerLayout);
        BannerAD bannerAd = new BannerAD(this, banner);
        bannerAd.loadBannerAd();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fileArrayList= (ArrayList<File>) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position",0);
        }
        initViews();

    }

    public void initViews(){
        showImagesAdapter=new ShowImagesAdapter(this, fileArrayList,FullViewActivity.this);
        binding.vpView.setAdapter(showImagesAdapter);
        binding.vpView.setCurrentItem(Position);

        binding.vpView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Position=arg0;
                System.out.println("Current position=="+Position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int num) {
            }
        });

        binding.imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean b=fileArrayList.get(Position).delete();
                        if (b){
                            deleteFileAA(Position);
                        }
                    }
                });
                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = ab.create();
                alert.setTitle("Do you want to delete?");
                alert.show();
            }
        });
        binding.imShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileArrayList.get(Position).getName().contains(".mp4")){
                    Log.d("SSSSS", "onClick: "+fileArrayList.get(Position)+"");
                    Utils.shareVideo(activity,fileArrayList.get(Position).getPath());
                }else {
                    Utils.shareImage(activity,fileArrayList.get(Position).getPath());
                }
            }
        });
        binding.imWhatsappShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileArrayList.get(Position).getName().contains(".mp4")){
                    Utils.shareImageVideoOnWhatsapp(activity,fileArrayList.get(Position).getPath(),true);
                }else {
                    Utils.shareImageVideoOnWhatsapp(activity,fileArrayList.get(Position).getPath(),false);
                }
            }
        });
        binding.imgback.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }
    public void deleteFileAA(int position){
        fileArrayList.remove(position);
        showImagesAdapter.notifyDataSetChanged();
        Utils.setToast(activity,"File Deleted");
        if (fileArrayList.size()==0){
            onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
