package com.sortscript.digitalkasaan.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.annotations.NotNull;
import com.sortscript.digitalkasaan.databinding.ActivitySplashBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.SessionManager;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        sessionManager = new SessionManager(this);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }

        String permission = Manifest.permission.CALL_PHONE;
        int res = this.checkCallingOrSelfPermission(permission);
        if (res == PackageManager.PERMISSION_GRANTED ) {
            new Handler().postDelayed(this::decideScreen, 2500);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String permissions[], @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new Handler().postDelayed(this::decideScreen, 2500);

                } else {
                    // permission was Denied
                    Toast.makeText(this, "Please Allow Permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            if (Environment.isExternalStorageManager()) {
                new Handler().postDelayed(this::decideScreen, 2500);
            } else {
                // permission was Denied
                Toast.makeText(this, "Please Allow Permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void decideScreen() {
        if (sessionManager.getLogin()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        finish();
    }
}