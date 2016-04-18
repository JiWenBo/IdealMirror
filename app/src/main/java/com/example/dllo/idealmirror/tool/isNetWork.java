package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dllo.idealmirror.R;

/**
 * 该类为了判断是否有网络
 * Created by dllo on 16/4/7.
 */
public class IsNetWork {
    public static boolean isNetWorkAvilable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) {

        } else {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if(networkInfos != null){
                for (int i = 0, count = networkInfos.length; i < count; i++) {
                    if(networkInfos[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
