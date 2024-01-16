package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.db.gbwhatsappdb.ADS.AdName;
import com.db.gbwhatsappdb.ADS.AppOpenManager;
import com.db.gbwhatsappdb.ADS.SharedPrefs;
import com.db.gbwhatsappdb.databinding.ActivitySplashScreenBinding;
import com.google.android.gms.ads.MobileAds;

public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    Handler handler = new Handler();
    private CountDownTimer countDownTimer = null;
    AppOpenManager appOpenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        MobileAds.initialize(this, initializationStatus -> {
        });

        SharedPrefs sharedPrefs = new SharedPrefs(getApplicationContext());
        sharedPrefs.loaddata();

        appOpenManager = new AppOpenManager(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            appOpenManager.fetchAd();
            countDownTimer = new CountDownTimer(6000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    long seconds = millisUntilFinished / 1000;

                    int i = Integer.parseInt(String.valueOf(seconds));

                    if (i == 0) {
                        if (AppOpenManager.isAdLoaded()) {
                            appOpenManager.showAdIfAvailable();
                            cancel();
                        }
                    }
                }

                @Override
                public void onFinish() {
                    if (!AppOpenManager.isAdLoaded()) {
                        intentToHomeScreen();
                    }
                }
            }.start();
        } else {
            new Handler().postDelayed(() -> intentToHomeScreen(), 5000);
        }

    }

    public void intentToHomeScreen() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("ADDEMO", Context.MODE_PRIVATE);
                String newapplink = sharedPreferences.getString(AdName.NewApp_link, "");
                String appversion = sharedPreferences.getString(AdName.VersionCode, "");
                Boolean forceupdate = sharedPreferences.getBoolean(String.valueOf(AdName.F_update), false);



                String appCurrentVersion = getAppVersion(getApplicationContext());

                if(!appCurrentVersion.equals(appversion)){
                    View view = getLayoutInflater().inflate(R.layout.ads_app_update,null);
                    setContentView(view);

                    TextView update = view.findViewById(R.id.btnUpdate);
                    TextView close = view.findViewById(R.id.btnClose);

                    if(forceupdate==true){
                        close.setVisibility(View.GONE);
                    }

                    update.setOnClickListener(view1 -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newapplink));
                        startActivity(intent);
                    });
                    close.setOnClickListener(view1 -> {
                        startActivity(new Intent(SplashScreenActivity.this, Screen1Activity.class));
                        finish();
                    });
                }else {
                    startActivity(new Intent(SplashScreenActivity.this, Screen1Activity.class));
                    finish();
                }

            }
        }, 2000);

    }

    public void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            Log.d("mmmm", "stop countdown");
        }
    }

    public static String getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AppVersionUtils", "Package name not found", e);
            return null;
        }
    }

}