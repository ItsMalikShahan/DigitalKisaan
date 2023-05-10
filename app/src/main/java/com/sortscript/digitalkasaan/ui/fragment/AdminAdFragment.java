package com.sortscript.digitalkasaan.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.FragmentAdminAdBinding;
import com.sortscript.digitalkasaan.databinding.FragmentHomeBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.ui.activity.AdminActivity;
import com.sortscript.digitalkasaan.ui.adapter.AdminAdAdapter;
import com.sortscript.digitalkasaan.ui.base.BaseFragment;

import java.util.ArrayList;

public class AdminAdFragment extends BaseFragment implements AdminAdAdapter.ItemClickListener {

    private FragmentAdminAdBinding binding;
    ArrayList<AdModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminAdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
        getAds();
    }

    private void getAds() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Ads");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                binding.rvAdminAds.removeAllViews();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AdModel adModel = dataSnapshot.getValue(AdModel.class);
                    assert adModel != null;

                            list.add(adModel);

                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        AdminAdAdapter adapter = new AdminAdAdapter(getContext(), list, this ,"");
        binding.rvAdminAds.setAdapter(adapter);
        hideLoading();
    }

    @Override
    public void itemClick(AdModel adModel) {
        Fragment adminAdDetailFragment = new AdminAdDetailFragment();
        Gson gson = new Gson();
        String myJson = gson.toJson(adModel);
        Bundle bundle = new Bundle();
        bundle.putString("AD", myJson);
        adminAdDetailFragment.setArguments(bundle);
        ((AdminActivity) getActivity()).replaceFragmentMethod(adminAdDetailFragment, AdminAdDetailFragment.class.getSimpleName(), true, false, getActivity());
    }

    @Override
    public void deleteAd(AdModel adModel) {

    }
}