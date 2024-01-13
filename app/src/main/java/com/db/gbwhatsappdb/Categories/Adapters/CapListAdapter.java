package com.db.gbwhatsappdb.Categories.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.db.gbwhatsappdb.Categories.CaptionListActivity;
import com.db.gbwhatsappdb.R;

public class CapListAdapter extends RecyclerView.Adapter<CapListAdapter.ViewHolder>
{
    CaptionListActivity captionListActivity;
    String[] datalist;

    public CapListAdapter(CaptionListActivity captionListActivity, String[] datalist) {
        this.captionListActivity = captionListActivity;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public CapListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cap_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CapListAdapter.ViewHolder holder, int position) {

        holder.text.setText(""+datalist[position]);

        holder.wa.setOnClickListener(view -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage("com.whatsapp");
            shareIntent.putExtra(Intent.EXTRA_TEXT, datalist[position]);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                captionListActivity.startActivity(shareIntent);
            } catch (Exception e) {
                Toast.makeText(captionListActivity, "Please install whatsapp first.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.copy.setOnClickListener(view -> {
            ((ClipboardManager) captionListActivity.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Text", datalist[position]));
            Toast.makeText(captionListActivity, "Text Copied!", Toast.LENGTH_SHORT).show();
        });

        holder.share.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", captionListActivity.getResources().getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", datalist[position]);
            captionListActivity.startActivity(Intent.createChooser(intent, "choose one"));
        });

    }

    @Override
    public int getItemCount() {
        return datalist.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView copy,wa,share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            wa = itemView.findViewById(R.id.one1);
            copy = itemView.findViewById(R.id.one2);
            share = itemView.findViewById(R.id.one3);
        }
    }
}
