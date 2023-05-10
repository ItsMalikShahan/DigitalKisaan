package com.sortscript.digitalkasaan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ActivitySettingBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.CategoryModel;
import com.sortscript.digitalkasaan.datamodel.model.fan.SessionManager;
import com.sortscript.digitalkasaan.ui.adapter.CategoryAdapter;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;

import java.util.ArrayList;

public class SettingActivity extends BaseActivity {

    private ActivitySettingBinding binding;
    ArrayList<CategoryModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        SessionManager sessionManager = new SessionManager(this);
        binding.tvUsername.setText(sessionManager.getobject().getUserName());

        binding.cvCreateAds.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAdsActivity.class));
        });

        binding.cvViewAds.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewMyAdActivity.class));
        });

        binding.cvLogout.setOnClickListener(v -> {
            sessionManager.setLogin(false);
            sessionManager.setObject(null);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }


}