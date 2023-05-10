package com.sortscript.digitalkasaan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ActivityMainBinding;
import com.sortscript.digitalkasaan.databinding.ActivityViewSingleAdBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;

public class ViewSingleAdActivity extends AppCompatActivity {

    private ActivityViewSingleAdBinding binding;
    AdModel adModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewSingleAdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        Gson gson = new Gson();
        adModel = gson.fromJson(getIntent().getStringExtra("AdModel"), AdModel.class);


        Glide.with(this).load(adModel.getImageUrl()).into(binding.ivProductImage);
        binding.tvProductName.setText(adModel.getTitle());
        binding.tvProductQuantity.setText(adModel.getQuantity());
        binding.tvProductPrice.setText(adModel.getPrice());
        binding.tvLocation.setText(adModel.getAddress());
    }
}