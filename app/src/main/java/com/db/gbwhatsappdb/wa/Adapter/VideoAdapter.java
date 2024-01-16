package com.db.gbwhatsappdb.wa.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.VideoPreviewActivity;
import com.db.gbwhatsappdb.wa.Models.Status;
import com.db.gbwhatsappdb.wa.Utils.Common;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    public static List<Status> videoList;
    private Context context;
    private final RelativeLayout container;

    public VideoAdapter(List<Status> videoList, RelativeLayout container) {
        this.videoList = videoList;
        this.container = container;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {

        final Status status = videoList.get(position);

        AdsManager adsManager = new AdsManager(context);
        InterstitialAD helper = new InterstitialAD(context, (Activity) context,adsManager);

        if (status.isApi30()) {
//            holder.save.setVisibility(View.GONE);
            Glide.with(context).load(status.getDocumentFile().getUri()).into(holder.imageView);
        } else {
//            holder.save.setVisibility(View.VISIBLE);
            Glide.with(context).load(status.getFile()).into(holder.imageView);
        }

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/mp4");
            if (status.isApi30()) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, status.getDocumentFile().getUri());
            } else {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.getFile().getAbsolutePath()));
            }
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    context.startActivity(Intent.createChooser(shareIntent, "Share image"));
                }
                @Override
                public void onInterstitialDismissed() {
                    context.startActivity(Intent.createChooser(shareIntent, "Share image"));
                }
            });

        });

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);

        holder.imageView.setOnClickListener(v -> {

            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    Intent intent = new Intent(context , VideoPreviewActivity.class);
                    intent.putExtra("pos",position);
                    context.startActivity(intent);
                }
                @Override
                public void onInterstitialDismissed() {
                    Intent intent = new Intent(context , VideoPreviewActivity.class);
                    intent.putExtra("pos",position);
                    context.startActivity(intent);
                }
            });
        });

        holder.save.setOnClickListener(v -> {
            helper.showCounterInterstitialAd(new InterstitialAD.AdLoadListeners() {
                @Override
                public void onAdLoadFailed() {
                    Common.copyFile(status, context, container);
                }
                @Override
                public void onInterstitialDismissed() {
                    Common.copyFile(status, context, container);
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

}
