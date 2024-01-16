package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
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
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        Native aNative = new Native(this);
        aNative.ShowNative(this, findViewById(R.id.native_container), 1);
        Native aNative1 = new Native(this);
        aNative1.ShowNative(this, findViewById(R.id.native_container1), 1);
        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this, this, adsManager);

        binding.setting.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
            });
        });

        binding.menu1.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, WhatsAppActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, WhatsAppActivity.class));
                }
            });
        });
        binding.menu2.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, WABusinessActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, WABusinessActivity.class));
                }
            });
        });
        binding.menu3.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, TextRepeaterActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, TextRepeaterActivity.class));
                }
            });
        });
        binding.menu4.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, DirectMessageActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, DirectMessageActivity.class));
                }
            });
        });
        binding.menu5.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, WaWebActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, WaWebActivity.class));
                }
            });
        });
        binding.menu6.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, FlipTextActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, FlipTextActivity.class));
                }
            });
        });
        binding.menu7.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, TextStyleActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, TextStyleActivity.class));
                }
            });
        });
        binding.menu8.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, TextArtActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, TextArtActivity.class));
                }
            });
        });
        binding.menu9.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, CaptionActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, CaptionActivity.class));
                }
            });
        });
        binding.menu10.setOnClickListener(view -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    startActivity(new Intent(MainActivity.this, SavedStatusActivity.class));
                }

                @Override
                public void onInterstitialDismissed() {
                    startActivity(new Intent(MainActivity.this, SavedStatusActivity.class));
                }
            });

        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(this, "Press Again to Exit..", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}