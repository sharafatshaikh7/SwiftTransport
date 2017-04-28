package com.swifttransport.ConstantClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sunil on 1/25/2017.
 */

//for cheking the internet connection available or not
public class NetworkCheck {

    Context mCtx;

    //for checking network connection
    public NetworkCheck(Context context)
    {
        mCtx=context;
    }

    public boolean isConnectinToInternet()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null)
        {
            NetworkInfo[] info =connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for(NetworkInfo aninfo : info)
                {
                    //cheking every state of network
                    if(aninfo.getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
