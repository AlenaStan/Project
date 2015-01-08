package com.example.lena.testscreen.oauth;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.auth.AuthenticationException;

/**
 * Created by lena on 04.01.2015.
 */
public class VkOAuthHelper {
    private static final String ACCESS_TOKEN ="access token" ;

    public static interface Callbacks {

        void onError(Exception e);
        void onSuccess();

    }

    //Refactor
    private static String sToken;

    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String AUTORIZATION_URL = "https://oauth.vk.com/authorize?client_id=4620384&scope=offline,wall,photos,status&redirect_uri=" + REDIRECT_URL + "&display=touch&response_type=token";
    private static final String TAG = VkOAuthHelper.class.getSimpleName();

    public static String sign(String url) {
        if (url.contains("?")) {
            return url + "&access_token="+sToken;
        } else {
            return url + "?access_token="+sToken;
        }
    }

    public static boolean isLogged() {
        return !TextUtils.isEmpty(sToken);
    }

    public static boolean proceedRedirectURL(Activity activity, String url, Callbacks callbacks) {
        if (url.startsWith(REDIRECT_URL)) {
            Uri uri = Uri.parse(url);
            String fragment = uri.getFragment();
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token");
            if (!TextUtils.isEmpty(accessToken)) {
                saveTokenToSharedPreferences(activity,accessToken);
                tokenFromSharedPreferences(activity,ACCESS_TOKEN);
                //TODO create account in account manager
                Log.d(TAG, "token " + accessToken);
                sToken = accessToken;
                callbacks.onSuccess();
                return true;
            } else {
                String error = parsedFragment.getQueryParameter("error");
                String errorDescription = parsedFragment.getQueryParameter("error_description");
                String errorReason = parsedFragment.getQueryParameter("error_reason");
                if (!TextUtils.isEmpty(error)) {
                    callbacks.onError(new AuthenticationException(error+", reason : " + errorReason +"("+errorDescription+")"));
                    return false;
                } else {
                    //Handle errors
                }
            }
        }
        return false;
    }
    public static void saveTokenToSharedPreferences(Context ctx, String accessToken) {

        try {
            String nToken = EncodeManager.encode(ctx, accessToken);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_TOKEN, nToken);
            edit.commit();
            Toast.makeText(ctx, nToken, Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    public static void tokenFromSharedPreferences(Context ctx, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        if(prefs.contains(key))
         try {
            String f = prefs.getString(key,"");
            String dToken = EncodeManager.decode(ctx, f);
            Toast.makeText(ctx, dToken, Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}