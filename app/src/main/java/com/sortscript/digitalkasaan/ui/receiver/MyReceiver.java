package com.sortscript.digitalkasaan.ui.receiver;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.sortscript.digitalkasaan.other.utils.NetworkAvailability;
import com.sortscript.digitalkasaan.R;

public class MyReceiver extends BroadcastReceiver {
    Dialog dialog;
    TextView netText;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        String status = NetworkAvailability.getConnectivityStatusString(context);
//        dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.customdialog);
        Button restartApp = (Button)dialog.findViewById(R.id.restartapp);
        netText =(TextView)dialog.findViewById(R.id.nettext);

        restartApp.setOnClickListener(v -> {
//            ((Activity) context).finish();
            Log.d("clickedbutton","yes");
//            Intent i = new Intent(context, MainActivity.class);
//            context.startActivity(i);
        });
        Log.d("network",status);
        if(status.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";
            dialog.show();
        }
//        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}