package com.sortscript.digitalkasaan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ActivityMainBinding;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;
import com.sortscript.digitalkasaan.ui.fragment.CategoryFragment;
import com.sortscript.digitalkasaan.ui.fragment.FavoriteFragment;
import com.sortscript.digitalkasaan.ui.fragment.HomeFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.tvTitle.setText("Home");
        Fragment homeFragment = new HomeFragment();
        replaceFragmentMethod(homeFragment, HomeFragment.class.getSimpleName(), false, true, MainActivity.this);

        binding.bottomNav.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.home:
                            binding.tvTitle.setText("Home");
                            replaceFragmentMethod(homeFragment, HomeFragment.class.getSimpleName(), false, true, MainActivity.this);
                            return true;
                        case R.id.favorite:
                            binding.tvTitle.setText("Favorite");
                            Fragment favoriteFragment = new FavoriteFragment();
                            replaceFragmentMethod(favoriteFragment, FavoriteFragment.class.getSimpleName(), false, true, MainActivity.this);
                            return true;
                        case R.id.category:
                            binding.tvTitle.setText("Category");
                            Fragment categoryFragment = new CategoryFragment();
                            replaceFragmentMethod(categoryFragment, CategoryFragment.class.getSimpleName(), false, true, MainActivity.this);
                            return true;
                    }
                    return false;
                });

        binding.ivDashboard.setOnClickListener(v->{
            startActivity(new Intent(this , SettingActivity.class));
        });
    }


}