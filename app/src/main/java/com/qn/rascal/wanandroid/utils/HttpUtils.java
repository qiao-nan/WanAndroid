package com.qn.rascal.wanandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rascal on 2019/10/23.
 */

public class HttpUtils {
    public static boolean isNetWorkAvailable(Context context){
        if(context!=null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if(info!=null){
                return info.isAvailable();
            }
        }
        return false;
    }
}
