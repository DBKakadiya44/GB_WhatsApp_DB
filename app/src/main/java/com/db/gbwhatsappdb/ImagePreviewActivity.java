package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
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
            Common.copyFile(status, this, binding.relative);
        });

        binding.share.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpg");
            if (status.isApi30()) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, status.getDocumentFile().getUri());
            } else {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.getFile().getAbsolutePath()));
            }
            startActivity(Intent.createChooser(shareIntent, "Share image"));
        });


    }
}