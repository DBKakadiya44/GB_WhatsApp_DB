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

import com.db.gbwhatsappdb.R;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ViewHolder>
{
    public Context ctx;
    public String[] items;
    public String tab_string;
    public ArtAdapter(Context context, String[] strArr, String str) {
        this.items = strArr;
        this.ctx = context;
        this.tab_string = str;
    }

    @NonNull
    @Override
    public ArtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_art, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtAdapter.ViewHolder holder, int position) {
        holder.text.setText(""+items[position]);

        holder.wa.setOnClickListener(view -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage("com.whatsapp");
                shareIntent.putExtra(Intent.EXTRA_TEXT, items[position]);
                shareIntent.setType("text/plain");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    ctx.startActivity(shareIntent);
                } catch (Exception e) {
                    Toast.makeText(ctx, "Please install whatsapp first.", Toast.LENGTH_SHORT).show();
                }
        });

        holder.copy.setOnClickListener(view -> {
            ((ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Text", items[position]));
            Toast.makeText(ctx, "Text Copied!", Toast.LENGTH_SHORT).show();
        });

        holder.share.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", ctx.getResources().getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", items[position]);
            ctx.startActivity(Intent.createChooser(intent, "choose one"));
        });

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView wa,share,copy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            wa = (ImageView) itemView.findViewById(R.id.one1);
            share = (ImageView) itemView.findViewById(R.id.one3);
            copy = (ImageView) itemView.findViewById(R.id.one2);
        }
    }
}
