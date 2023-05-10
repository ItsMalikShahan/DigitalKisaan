package com.sortscript.digitalkasaan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ActivityAdminBinding;
import com.sortscript.digitalkasaan.databinding.ActivitySplashBinding;
import com.sortscript.digitalkasaan.ui.fragment.AdminAdFragment;
import com.sortscript.digitalkasaan.ui.fragment.HomeFragment;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment adminAdFragment = new AdminAdFragment();
        replaceFragmentMethod(adminAdFragment, AdminAdFragment.class.getSimpleName(), false, true, AdminActivity.this);


        getSupportActionBar().hide();
    }

    public void replaceFragmentMethod(Fragment frag, String tag, Boolean addToStack, Boolean clearStack,
                                      FragmentActivity fa) {
        if (clearStack)
            fa.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_admin, frag, tag);
        if (addToStack)
            ft.addToBackStack(tag);
        ft.commit();

    }
}