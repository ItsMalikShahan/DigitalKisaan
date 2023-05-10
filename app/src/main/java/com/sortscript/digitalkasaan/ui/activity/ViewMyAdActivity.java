package com.sortscript.digitalkasaan.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sortscript.digitalkasaan.databinding.ActivityMainBinding;
import com.sortscript.digitalkasaan.databinding.ActivityViewMyAdBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.datamodel.model.fan.SessionManager;
import com.sortscript.digitalkasaan.ui.adapter.AdAdapter;
import com.sortscript.digitalkasaan.ui.adapter.AdminAdAdapter;
import com.sortscript.digitalkasaan.ui.adapter.HomeAdapter;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;

import java.util.ArrayList;

public class ViewMyAdActivity extends BaseActivity implements AdminAdAdapter.ItemClickListener {

    private ActivityViewMyAdBinding binding;
    ArrayList<AdModel> list = new ArrayList<>();
    ArrayList<String> listFav = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMyAdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        showLoading();
       getMyAds();

    }

    private void getMyAds() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Ads");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                binding.rvMyAds.removeAllViews();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AdModel adModel = dataSnapshot.getValue(AdModel.class);
                    assert adModel != null;
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (adModel.getUserId().equals(userId)) {
                            list.add(adModel);
                    }
                }
                setAdapter();
                hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        AdminAdAdapter homeAdapter = new AdminAdAdapter(this, list, this ,"MyAds");
        binding.rvMyAds.setAdapter(homeAdapter);
    }

    @Override
    public void itemClick(AdModel adModel) {

    }

    @Override
    public void deleteAd(AdModel adModel) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan");
        reference.child("Ads").child(adModel.getAdId()).removeValue().addOnCompleteListener(task -> {
           showToast("Ad Deleted");
        });
    }

}