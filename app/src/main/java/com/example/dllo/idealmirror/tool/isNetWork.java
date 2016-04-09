package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 该类为了判断是否有网络
 * Created by dllo on 16/4/7.
 */
public class isNetWork {
    public static boolean isnetWorkAvilable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) {

        } else {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if(networkInfos != null){
                for (int i = 0, count = networkInfos.length; i < count; i++) {
                    if(networkInfos[i].getState() == NetworkInfo.State.CONNECTED){
                        ToastUtils.showToast(context,"网络正常");
                        return true;
                    }
                }
            }
        }
        ToastUtils.showToast(context,"网络非正常");
        return false;
    }

}
