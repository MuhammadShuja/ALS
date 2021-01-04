package com.alllinkshare.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alllinkshare.core.ALS;

public class NetworkConnectivity {
    public static boolean isConnected(){
        ConnectivityManager connectivityManager = ((ConnectivityManager) ALS.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}