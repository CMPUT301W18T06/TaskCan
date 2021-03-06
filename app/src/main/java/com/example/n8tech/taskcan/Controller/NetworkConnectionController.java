package com.example.n8tech.taskcan.Controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AlanJ on 2018-03-23.
 */

public class NetworkConnectionController {
    private static ConnectivityManager connManager;
    private static NetworkInfo mWifi;
    private static NetworkInfo mData;

    public static boolean isConnected(Context context) {
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        
        return mWifi.isConnected() || mData.isConnected();
    }
}
