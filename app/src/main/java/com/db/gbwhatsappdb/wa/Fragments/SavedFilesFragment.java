package com.db.gbwhatsappdb.wa.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.db.gbwhatsappdb.ADS.AdsManager;
import com.db.gbwhatsappdb.ADS.InterstitialAD;
import com.db.gbwhatsappdb.ADS.Native;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.wa.Adapter.FilesAdapter;
import com.db.gbwhatsappdb.wa.Models.Status;
import com.db.gbwhatsappdb.wa.Utils.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedFilesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final List<Status> savedFilesList = new ArrayList<>();
    private final Handler handler = new Handler();
    private FilesAdapter filesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView no_files_found;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Native aNative = new Native(getActivity());
        aNative.ShowNative(getActivity(), view.findViewById(R.id.native_container),1);

        AdsManager adsManager = new AdsManager(getContext());
        InterstitialAD helper = new InterstitialAD(getContext(),getActivity(),adsManager);

        recyclerView = view.findViewById(R.id.recyclerViewFiles);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutFiles);
        progressBar = view.findViewById(R.id.progressBar);
        no_files_found = view.findViewById(R.id.no_files_found);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark)
                , ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
                ContextCompat.getColor(requireActivity(), R.color.black),
                ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark));

        swipeRefreshLayout.setOnRefreshListener(this::getFiles);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Common.GRID_COUNT));

        getFiles();

    }

    private void getFiles() {

        final File app_dir = new File(Common.APP_DIR);

        if (app_dir.exists() ||
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            no_files_found.setVisibility(View.GONE);

            new Thread(() -> {
                File[] savedFiles;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    File f = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM
                    ) + File.separator + "status_saver");
                    savedFiles = f.listFiles();
                } else {
                    savedFiles = app_dir.listFiles();
                }
                savedFilesList.clear();

                if (savedFiles != null && savedFiles.length > 0) {

                    Arrays.sort(savedFiles);
                    for (File file : savedFiles) {
                        Status status = new Status(file, file.getName(), file.getAbsolutePath());

                        savedFilesList.add(status);
                    }

                    handler.post(() -> {

                        filesAdapter = new FilesAdapter(savedFilesList);
                        recyclerView.setAdapter(filesAdapter);
                        filesAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    });

                } else {

                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        no_files_found.setVisibility(View.VISIBLE);
//                                Toast.makeText(getActivity(), "Dir doest not exists", Toast.LENGTH_SHORT).show();
                    });

                }
                swipeRefreshLayout.setRefreshing(false);
            }).start();

        } else {
            no_files_found.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

//    private Bitmap getThumbnail(Status status) {
//        return a.gautham.statusdownloader.Utils.ThumbnailUtils.createVideoThumbnail(status.getFile().getAbsolutePath(),
//                3);
//    }
}
