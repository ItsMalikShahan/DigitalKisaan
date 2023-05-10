package com.sortscript.digitalkasaan.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.FragmentAdminAdBinding;
import com.sortscript.digitalkasaan.databinding.FragmentAdminAdDetailBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.ui.activity.AdminActivity;
import com.sortscript.digitalkasaan.ui.base.BaseFragment;

public class AdminAdDetailFragment extends BaseFragment {

    private FragmentAdminAdDetailBinding binding;
    AdModel adModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        adModel = gson.fromJson(getArguments().getString("AD"), AdModel.class);
        showToast(adModel.getStatus());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminAdDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext()).load(adModel.getImageUrl()).placeholder(R.drawable.photo).into(binding.ivProductImage);
        binding.tvProductName.setText(adModel.getTitle());
        binding.tvProductPrice.setText(adModel.getPrice());
        binding.tvProductQuantity.setText(adModel.getQuantity());
        binding.tvProductNumber.setText(adModel.getNumber());
        binding.tvProductAddress.setText(adModel.getAddress());
        binding.tvProductCategory.setText(adModel.getCategory());
        binding.tvProductStatus.setText(adModel.getStatus());

        binding.btnReject.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Ads");
            reference.child(adModel.getAdId()).child("status").setValue("Reject");
            Fragment adminAdFragment = new AdminAdFragment();
            ((AdminActivity) getActivity()).replaceFragmentMethod(adminAdFragment, AdminAdFragment.class.getSimpleName(), true, true, getActivity());
        });

        binding.btnAccept.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Ads");
            reference.child(adModel.getAdId()).child("status").setValue("Approved");
            Fragment adminAdFragment = new AdminAdFragment();
            ((AdminActivity) getActivity()).replaceFragmentMethod(adminAdFragment, AdminAdFragment.class.getSimpleName(), true, true, getActivity());
        });
    }
}