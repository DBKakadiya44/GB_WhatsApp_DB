package com.db.gbwhatsappdb.Categories.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.db.gbwhatsappdb.Categories.CaptionActivity;
import com.db.gbwhatsappdb.Categories.CaptionListActivity;
import com.db.gbwhatsappdb.R;

public class CaptionAdapter extends RecyclerView.Adapter<CaptionAdapter.ViewHolder>
{
    CaptionActivity captionActivity;
    String[] capNamelist;
    int[] arraylength;
    public CaptionAdapter(CaptionActivity captionActivity, String[] capNamelist, int[] arraylength) {
        this.captionActivity = captionActivity;
        this.capNamelist = capNamelist;
        this.arraylength = arraylength;
    }

    @NonNull
    @Override
    public CaptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caption,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionAdapter.ViewHolder holder, int position) {
        holder.name.setText(""+capNamelist[position]);
        holder.cnt.setText(""+arraylength[position]);
        holder.cc.setOnClickListener(view -> {
            captionActivity.startActivity(new Intent(captionActivity , CaptionListActivity.class).putExtra("pos",position));
        });
    }

    @Override
    public int getItemCount() {
        return capNamelist.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cc;
        TextView name,cnt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cc = itemView.findViewById(R.id.ccCaption);
            name = itemView.findViewById(R.id.capname);
            cnt = itemView.findViewById(R.id.capsize);
        }
    }
}
