package com.github.alenastan.vkclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.alenastan.vkclient.oauth.EncryptHelper;

/**
 * Created by lena on 18.01.2015.
 */
//TODO rename to some another name for example Session
public class AccessToken {

    public static final String ACCESS_TOKEN = "access_token";

    //TODO why this is static?
    private static String accessToken;

    //TODO why this is static?
    public static String get(){
        return accessToken;
    }

    //TODO why this method exists?
    public  String getAccessToken(Context ctx){
         return tokenFromSharedPreferences(ctx);
     }

    public void saveToken(String token,Context ctx){
        saveTokenToSharedPreferences(token,ctx);
    }

    private  static void saveTokenToSharedPreferences(String token,Context context) {
        String encToken = null;
        try {
            encToken = EncryptHelper.encrypt(context, token);
            } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(ACCESS_TOKEN, encToken);
        edit.commit();
    }

    public static String tokenFromSharedPreferences(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sToken = prefs.getString(ACCESS_TOKEN,"");
        try {
            accessToken = EncryptHelper.decrypt(context, sToken);
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
