package com.db.gbwhatsappdb.WABusiness;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.BannerAD;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.WABusiness.fragment.WhatsappImageFragment;
import com.db.gbwhatsappdb.WABusiness.fragment.WhatsappQImageFragment;
import com.db.gbwhatsappdb.WABusiness.fragment.WhatsappQVideoFragment;
import com.db.gbwhatsappdb.WABusiness.fragment.WhatsappVideoFragment;
import com.db.gbwhatsappdb.WABusiness.util_items.Utils;
import com.db.gbwhatsappdb.databinding.ActivityWabusinessBinding;
import com.db.gbwhatsappdb.databinding.DialogWhatsappPermissionBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WABusinessActivity extends AppCompatActivity {
    private ActivityWabusinessBinding binding;
    private WABusinessActivity activity;
    private File[] allfiles;
    private ArrayList<Uri> fileArrayList;
    ProgressDialog progressDialog;
    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wabusiness);
        activity = this;

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        LinearLayout banner = findViewById(R.id.bannerLayout);
        BannerAD bannerAd = new BannerAD(this, banner);
        bannerAd.loadBannerAd();

        AdsManager adsManager = new AdsManager(this);
        InterstitialAD helper = new InterstitialAD(this,this,adsManager);

        Utils.createFileFolder();

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(0);
        }
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.tvAllowAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 29) {
                    allowacess();
                } else {
                    Utils.OpenApp(activity, "com.whatsapp.w4b");
                }
            }
        });

        binding.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callGalleryActivity();
            }
        });

        fileArrayList = new ArrayList<>();
        initProgress();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if (getContentResolver().getPersistedUriPermissions().size() > 0) {
                progressDialog.show();
                new LoadAllFiles().execute();
                binding.tvAllowAccess.setVisibility(View.GONE);
            } else {
                binding.tvAllowAccess.setVisibility(View.VISIBLE);
            }
        } else {
            setupViewPager(binding.viewpager);
            binding.tabs.setupWithViewPager(binding.viewpager);

        }

        binding.tvAllowAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allowacess();
            }
        });
    }

    private void allowacess() {
        Dialog dialog = new Dialog(activity, R.style.SheetDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogWhatsappPermissionBinding dialogWhatsappPermissionBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_whatsapp_permission, null, false);
        dialog.setContentView(dialogWhatsappPermissionBinding.getRoot());
        dialogWhatsappPermissionBinding.tvAllow.setOnClickListener(v -> {
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    StorageManager sm = (StorageManager) activity.getSystemService(Context.STORAGE_SERVICE);
                    Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String startDir = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
                    Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
                    String scheme = uri.toString();
                    scheme = scheme.replace("/root/", "/document/");
                    scheme += "%3A" + startDir;
                    uri = Uri.parse(scheme);
                    intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                    startActivityForResult(intent, 2001);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });

        dialogWhatsappPermissionBinding.tvAllowBusiness.setOnClickListener(v -> {
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    StorageManager sm = (StorageManager) activity.getSystemService(Context.STORAGE_SERVICE);
                    Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String startDir = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses";
                    Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
                    String scheme = uri.toString();
                    scheme = scheme.replace("/root/", "/document/");
                    scheme += "%3A" + startDir;
                    uri = Uri.parse(scheme);
                    intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                    startActivityForResult(intent, 2002);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private boolean checkPermissions(int type) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(activity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) (activity),
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), type);
            return false;
        } else {
            if (type == 102) {
                callWhatsappActivity();
            } else if (type == 105) {
                callGalleryActivity();
            }

        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callWhatsappActivity();
            }
            return;
        } else if (requestCode == 105) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callGalleryActivity();
            }
            return;
        }
    }

    public void callWhatsappActivity() {
        Intent i = new Intent(activity, WABusinessActivity.class);
        startActivity(i);
    }

    public void callGalleryActivity() {
        Intent i = new Intent(activity, GalleryActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }


    public void initProgress() {
        progressDialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading Status. Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new WhatsappImageFragment(), "Photos");
        adapter.addFragment(new WhatsappVideoFragment(), "Videos");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2001 || requestCode == 2002 && resultCode == RESULT_OK) {
                Uri dataUri = data.getData();
                if (dataUri.toString().contains(".Statuses")) {
                    getContentResolver().takePersistableUriPermission(dataUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    progressDialog.show();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        new LoadAllFiles().execute();
                    }
                } else {
                    Utils.infoDialog(activity, "Wrong Folder", "You have seleced wrong folder. please select .Statuses...");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    class LoadAllFiles extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... furl) {
            DocumentFile documentFile = DocumentFile.fromTreeUri(activity, activity.getContentResolver().getPersistedUriPermissions().get(0).getUri());
            for (DocumentFile file : documentFile.listFiles()) {
                if (file.isDirectory()) {

                } else {
                    if (!file.getName().equals(".nomedia")) {
                        fileArrayList.add(file.getUri());
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            adapter.addFragment(new WhatsappQImageFragment(fileArrayList), "Photos");
            adapter.addFragment(new WhatsappQVideoFragment(fileArrayList), "Videos");
            binding.viewpager.setAdapter(adapter);
            binding.viewpager.setOffscreenPageLimit(1);
            binding.tabs.setupWithViewPager(binding.viewpager);
            binding.tvAllowAccess.setVisibility(View.GONE);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
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
