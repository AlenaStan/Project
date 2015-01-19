package com.github.alenastan.vkclient.oauth;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.github.alenastan.vkclient.AccessToken;
import org.apache.http.auth.AuthenticationException;

/**
 * Created by lena on 12.01.2015.
 */
public class VkOAuthHelper {
    public static interface Callbacks {

        void onError(Exception e);
        void onSuccess(String accessToken);
    }

    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String AUTORIZATION_URL = "https://oauth.vk.com/authorize?client_id=4620384&scope=offline,wall,photos,status&redirect_uri=" + REDIRECT_URL + "&display=touch&response_type=token";
    private static final String TAG = VkOAuthHelper.class.getSimpleName();

    public static boolean proceedRedirectURL(Activity activity, String url, Callbacks callbacks) {
        if (url.startsWith(REDIRECT_URL)) {
            Uri uri = Uri.parse(url);
            String fragment = uri.getFragment();
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token");
            if (!TextUtils.isEmpty(accessToken)) {
                Log.d(TAG, "token " + accessToken);
                callbacks.onSuccess(accessToken);
                return true;
            } else {
                String error = parsedFragment.getQueryParameter("error");
                String errorDescription = parsedFragment.getQueryParameter("error_description");
                String errorReason = parsedFragment.getQueryParameter("error_reason");
                if (!TextUtils.isEmpty(error)) {
                    callbacks.onError(new AuthenticationException(error+", reason : " + errorReason +"("+errorDescription+")"));
                    return false;

                }
            }
        }
        return false;
    }
    public static String sign(String url) {
        if (url.contains("?")) {
            return url + "&access_token="+ AccessToken.get();
        } else {
            return url + "?access_token="+ AccessToken.get();
        }
    }
}
