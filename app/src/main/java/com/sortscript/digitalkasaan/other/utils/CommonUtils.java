package com.sortscript.digitalkasaan.other.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.sortscript.digitalkasaan.R;

public class CommonUtils {

    public static ProgressDialog showLoadingDialog(Context context , Boolean cancelable){
        ProgressDialog progressDialog = new ProgressDialog(context);

        if (context!=null)progressDialog.show();
        if (progressDialog.getWindow() != null){
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(cancelable);
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return progressDialog;
    }

}
