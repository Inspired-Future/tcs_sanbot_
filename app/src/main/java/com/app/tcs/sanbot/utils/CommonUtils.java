package com.app.tcs.sanbot.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.app.tcs.sanbot.R;


public class CommonUtils {

    private CommonUtils() {
    }

    public static boolean isOnline(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        boolean flag = netInfo != null && netInfo.isConnected();
        if (!flag) {
            errorMsg(context, context.getString(R.string.str_network_error));
        }
        return flag;
    }


    public static void errorMsg(final Context context, final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

}



