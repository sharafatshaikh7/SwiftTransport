package com.swifttransport.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Sunil on 1/25/2017.
 */

public class PreferenceServices {

    public static PreferenceServices mInstance;
    private static Context mContext;
    public static final String TAG="Swifttrasport";
    public static final String DEFAULT = "NoData";

    public static final String LoginStatus="LoginStatus";
    public static final String ProfileData="profile";


    public PreferenceServices(Context context) {

        mContext=context;
    }

    //making a singleton class
    public static synchronized PreferenceServices getInstance(Context context) {
        if(mInstance == null)
        {
            mContext=context;
            mInstance= new PreferenceServices(context);
            return mInstance;
        }
        return mInstance;
    }
    //getting a statis preference object
    public SharedPreferences getpref()
    {
        SharedPreferences preferences=mContext.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return preferences;
    }
    //save the Login status
    public void saveUserLoginStatus(String loginStatus) {
        SharedPreferences.Editor editor = getpref().edit();
        editor.putString(LoginStatus, loginStatus);
        editor.commit();
    }
    //get the Login status
    public String getUserLoginStatus() {
        return getpref().getString(LoginStatus, "false");
    }

    //save the Login status
    public void saveProfileImage(String profile) {
        SharedPreferences.Editor editor = getpref().edit();
        editor.putString(ProfileData, profile);
        editor.commit();
    }
    //get the Login status
    public String getProfileImage() {
        return getpref().getString(ProfileData, "false");
    }

}
