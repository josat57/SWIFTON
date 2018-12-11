package com.swifton.swifton.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetCheck {

    //public static User currentUsers;

   // public static String convertCodeToStatus(String status){}

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if(info != null){

                if(info.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        } return false;
    }
}
