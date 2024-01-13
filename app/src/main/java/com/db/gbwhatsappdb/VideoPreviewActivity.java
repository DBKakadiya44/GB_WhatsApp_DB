package com.db.gbwhatsappdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.db.gbwhatsappdb.databinding.ActivityVideoPreviewBinding;
import com.db.gbwhatsappdb.wa.Adapter.VideoAdapter;
import com.db.gbwhatsappdb.wa.Models.Status;
import com.db.gbwhatsappdb.wa.Utils.Common;

public class VideoPreviewActivity extends AppCompatActivity {
    ActivityVideoPreviewBinding binding;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        pos = getIntent().getIntExtra("pos",0);

        Status status = VideoAdapter.videoList.get(pos);

        FrameLayout mediaControls = findViewById(R.id.videoViewWrapper);
        VideoView videoView = findViewById(R.id.video_full);

        MediaController mediaController = new MediaController(this, false);

        videoView.setOnPreparedListener(mp -> {

            mp.start();
            mediaController.show(0);
            mp.setLooping(true);
        });

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);

        if (status.isApi30()) {
            videoView.setVideoURI(status.getDocumentFile().getUri());
        } else {
            videoView.setVideoURI(Uri.fromFile(status.getFile()));
        }
        videoView.requestFocus();

        ((ViewGroup) mediaController.getParent()).removeView(mediaController);

        if (mediaControls.getParent() != null) {
            mediaControls.removeView(mediaController);
        }

        mediaControls.addView(mediaController);


        binding.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/mp4");
            if (status.isApi30()) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, status.getDocumentFile().getUri());
            } else {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.getFile().getAbsolutePath()));
            }
            startActivity(Intent.createChooser(shareIntent, "Share image"));

        });

        binding.download.setOnClickListener(v -> Common.copyFile(status, this, binding.relative));

    }
}