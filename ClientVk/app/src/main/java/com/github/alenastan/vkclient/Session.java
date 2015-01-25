package com.github.alenastan.vkclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.alenastan.vkclient.oauth.EncryptHelper;

/**
 * Created by lena on 18.01.2015.
 */
public class Session {

    public static final String ACCESS_TOKEN = "access_token";
    private static Session session;
    protected static String token;

    private Session () {

    }
    public  Session start(){
        if( session == null){
            session = new Session();

        }
        return session;
    }

    public  String getToken(){
         return token;
        }


    protected  void saveTokenToSharedPreferences(String s,Context context) {
        token = s;
        String encToken = null;
        try {
              encToken = EncryptHelper.encrypt(context,s);
            } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(ACCESS_TOKEN, encToken);
        edit.commit();

    }

    public static String getTokenFromSharedPreferences(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sToken = prefs.getString(ACCESS_TOKEN,"");
        try {
            sToken = EncryptHelper.decrypt(context, sToken);
            return sToken;
            } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
