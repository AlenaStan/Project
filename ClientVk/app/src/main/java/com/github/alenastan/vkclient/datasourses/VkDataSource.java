package com.github.alenastan.vkclient.datasourses;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.github.alenastan.vkclient.Api;
import com.github.alenastan.vkclient.CoreApplication;

import com.github.alenastan.vkclient.oauth.VkOAuthHelper;

import java.io.InputStream;

/**
 * Created by lena on 12.01.2015.
 */
public class VkDataSource extends HttpDataSource {

    public static final String KEY = "VkDataSource";

    public static VkDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        String signUrl = VkOAuthHelper.sign(p);
        String versionValue = Uri.parse(signUrl).getQueryParameter(Api.VERSION_PARAM);
        if (TextUtils.isEmpty(versionValue)) {
            signUrl = signUrl + "&" + Api.VERSION_PARAM + "=" + Api.VERSION_VALUE;
        }
        return super.getResult(signUrl);
    }

}