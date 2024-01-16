package com.db.gbwhatsappdb.wa.Fragments;

import android.content.UriPermission;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.wa.Adapter.VideoAdapter;
import com.db.gbwhatsappdb.wa.Models.Status;
import com.db.gbwhatsappdb.wa.Utils.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class VideoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final List<Status> videoList = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private RelativeLayout container;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView messageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerViewVideo);
        progressBar = view.findViewById(R.id.prgressBarVideo);
        container = view.findViewById(R.id.videos_container);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        messageTextView = view.findViewById(R.id.messageTextVideo);

        Native aNative = new Native(getActivity());
        aNative.ShowNative(getActivity(), view.findViewById(R.id.native_container),1);

        AdsManager adsManager = new AdsManager(getContext());
        InterstitialAD helper = new InterstitialAD(getContext(),getActivity(),adsManager);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark)
                , ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
                ContextCompat.getColor(requireActivity(), R.color.black),
                ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark));

        swipeRefreshLayout.setOnRefreshListener(this::getStatus);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Common.GRID_COUNT));

        getStatus();

        super.onViewCreated(view, savedInstanceState);
    }

    private void getStatus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            executeNew();

        } else if (Common.STATUS_DIRECTORY.exists()) {

            executeOld();

        } else {
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText("Cant find WhatsApp Dir");
            Toast.makeText(getActivity(), "Cant find WhatsApp Dir", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void executeNew() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            List<UriPermission> list = requireActivity().getContentResolver().getPersistedUriPermissions();

            DocumentFile file = DocumentFile.fromTreeUri(requireActivity(), list.get(0).getUri());

            videoList.clear();

            if (file == null) {
                mainHandler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    messageTextView.setVisibility(View.VISIBLE);
                    messageTextView.setText("Cant find WhatsApp Dir");
                    Toast.makeText(getActivity(), "Cant find WhatsApp Dir", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                });
                return;
            }

            DocumentFile[] statusFiles = file.listFiles();

            if (statusFiles.length <= 0) {
                mainHandler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    messageTextView.setVisibility(View.VISIBLE);
                    messageTextView.setText("Cant find WhatsApp Dir");
                    Toast.makeText(getActivity(), "Cant find WhatsApp Dir", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                });
                return;
            }

            for (DocumentFile documentFile : statusFiles) {
                Status status = new Status(documentFile);

                if (status.isVideo()) {
                    videoList.add(status);
                }
            }

            mainHandler.post(() -> {

                if (videoList.size() <= 0) {
                    messageTextView.setVisibility(View.VISIBLE);
                    messageTextView.setText("Cant find WhatsApp Dir");
                } else {
                    messageTextView.setVisibility(View.GONE);
                    messageTextView.setText("");
                }

                videoAdapter = new VideoAdapter(videoList, container);
                recyclerView.setAdapter(videoAdapter);
                videoAdapter.notifyItemRangeChanged(0, videoList.size());
                progressBar.setVisibility(View.GONE);
            });

            swipeRefreshLayout.setRefreshing(false);

        });
    }

    private void executeOld() {

        Executors.newSingleThreadExecutor().execute(() -> {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            File[] statusFiles = Common.STATUS_DIRECTORY.listFiles();
            videoList.clear();

            if (statusFiles != null && statusFiles.length > 0) {

                Arrays.sort(statusFiles);
                for (File file : statusFiles) {
                    Status status = new Status(file, file.getName(), file.getAbsolutePath());

                    if (status.isVideo()) {
                        videoList.add(status);
                    }

                }

                mainHandler.post(() -> {

                    if (videoList.size() <= 0) {
                        messageTextView.setVisibility(View.VISIBLE);
                        messageTextView.setText("Cant find WhatsApp Dir");
                    } else {
                        messageTextView.setVisibility(View.GONE);
                        messageTextView.setText("");
                    }

                    videoAdapter = new VideoAdapter(videoList, container);
                    recyclerView.setAdapter(videoAdapter);
                    videoAdapter.notifyItemRangeChanged(0, videoList.size());
                    progressBar.setVisibility(View.GONE);
                });

            } else {

                mainHandler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    messageTextView.setVisibility(View.VISIBLE);
                    messageTextView.setText("Cant find WhatsApp Dir");
                    Toast.makeText(getActivity(), "Cant find WhatsApp Dir", Toast.LENGTH_SHORT).show();
                });

            }
            swipeRefreshLayout.setRefreshing(false);

        });

    }

}
