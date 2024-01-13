package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.gbwhatsappdb.Categories.Adapters.ArtAdapter;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityTextArtBinding;

public class TextArtActivity extends AppCompatActivity {
    ActivityTextArtBinding binding;
    RecyclerView rv_Stylish_art;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextArtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        ArtAdapter adapter = new ArtAdapter(this, new String[]{".•♥•.¸¸.•♥••♥•.¸¸.•♥•.", "❋. _ _ .❋", "♥•**•♥", "*......*", "L●L", "✪☻✪☻", "✿¨¯`✿", "»♥", "░░", "▒░░░▒▓", "*´♥`*", "●́‿●", "♬♪♫", "◦'⌣'◦", "ξ◕◡◕ξ", "❆❆", "╠♥╣", "︻┳═一", "꧁༒☬☬༒꧂", "꧁༺༻꧂", "꧁༒♛♛༒꧂", "◥꧁དཌ꧂◤", "亗『』亗", "༒☬〖ℳℜ〗"}, "");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.rvStylishArt.setLayoutManager(manager);
        binding.rvStylishArt.setAdapter(adapter);
    }

}