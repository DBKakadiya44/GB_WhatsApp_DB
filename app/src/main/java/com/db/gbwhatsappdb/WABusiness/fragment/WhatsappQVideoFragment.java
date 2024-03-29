package com.db.gbwhatsappdb.WABusiness.fragment;

import static androidx.databinding.DataBindingUtil.inflate;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.db.gbwhatsappdb.ADS.Native;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.WABusiness.adapters.WhatsappStatusAdapter;
import com.db.gbwhatsappdb.WABusiness.all_interfaces.FileListWhatsappClickInterface;
import com.db.gbwhatsappdb.WABusiness.wa_model.WhatsappStatusModel;
import com.db.gbwhatsappdb.databinding.FragmentWhatsappImageBinding;

import java.io.File;
import java.util.ArrayList;
public class WhatsappQVideoFragment extends Fragment implements FileListWhatsappClickInterface {
    FragmentWhatsappImageBinding binding;
    private File[] allfiles;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;
    private ArrayList<Uri> fileArrayList;
    public WhatsappQVideoFragment(ArrayList<Uri> fileArrayList) {
        this.fileArrayList = fileArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.fragment_whatsapp_image, container, false);
        initViews();

        Native aNative = new Native(getActivity());
        aNative.ShowNative(getActivity(), binding.nativeContainer,1);

        return binding.getRoot();
    }

    private void initViews() {
        statusModelArrayList = new ArrayList<>();
        getData();
        binding.swiperefresh.setOnRefreshListener(() -> {
            statusModelArrayList = new ArrayList<>();
            getData();
            binding.swiperefresh.setRefreshing(false);
        });

    }

    private void getData() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            try {
                for (int i = 0; i < fileArrayList.size(); i++) {
                    WhatsappStatusModel whatsappStatusModel;
                    Uri uri = fileArrayList.get(i);
                    if (uri.toString().endsWith(".mp4")) {
                        whatsappStatusModel = new WhatsappStatusModel("WhatsStatus: " + (i + 1), uri, new File(uri.toString()).getAbsolutePath(), new File(uri.toString()).getName());
                        statusModelArrayList.add(whatsappStatusModel);
                    }
                }
                if (statusModelArrayList.size() != 0) {
                    binding.tvNoResult.setVisibility(View.GONE);
                } else {
                    binding.tvNoResult.setVisibility(View.VISIBLE);
                }
                whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(), statusModelArrayList, WhatsappQVideoFragment.this);
                binding.rvFileList.setAdapter(whatsappStatusAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getPosition(int position) {

    }
}
