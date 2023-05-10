package com.sortscript.digitalkasaan.ui.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.other.utils.CommonUtils;
import com.sortscript.digitalkasaan.ui.activity.SplashActivity;
import com.sortscript.digitalkasaan.ui.receiver.MyReceiver;

public abstract class BaseActivity extends AppCompatActivity {

    private BroadcastReceiver myReceiver = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        broadcastIntent();
    }

    public void broadcastIntent() {
        registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

    ProgressDialog mProgressDialog;

    protected void showLoading() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog = CommonUtils.showLoadingDialog(this, false);
        } else if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.showLoadingDialog(this, false);
        }
    }

//    public BaseActivity() {
//        LocaleUtils.updateConfiguration(this);
//    }

    public void hideLoading() {
        mProgressDialog.cancel();
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void replaceFragmentMethod(Fragment frag, String tag, Boolean addToStack, Boolean clearStack,
                                      FragmentActivity fa) {
        if (clearStack)
            fa.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, frag, tag);
        if (addToStack)
            ft.addToBackStack(tag);
        ft.commit();

    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(LocaleManager.setLocale(base));
//    }

//    public void setLocale(String lang) {
//        Locale locale = new Locale(lang);
////        Locale.setDefault(locale);
////        Configuration configuration = new Configuration();
////        configuration.setLocale(locale);
////        createConfigurationContext(configuration);
////        Context context;
////        Resources resources;
////        context = LocaleHelper.setLocale(this, "ur");
////        resources = context.getResources();
////        restartApplication();
//
//    }

    public void restartApplication() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
