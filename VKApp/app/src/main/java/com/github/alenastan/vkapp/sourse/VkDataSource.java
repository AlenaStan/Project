package com.github.alenastan.vkapp.sourse;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.github.alenastan.vkapp.Api;
import com.github.alenastan.vkapp.CoreApplication;
import com.github.alenastan.vkapp.autorization.VkOAuthHelper;

import java.io.InputStream;

/**
 * Created by lena on 25.01.2015.
 */
public class VkDataSource extends HttpDataSource {

    public static final String KEY = "VkDataSource";

    public static VkDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        String signUrl = VkOAuthHelper.signUrl(p);
        String versionValue = Uri.parse(signUrl).getQueryParameter(Api.VERSION_PARAM);
        if (TextUtils.isEmpty(versionValue)) {
            signUrl = signUrl + "&" + Api.VERSION_PARAM + "=" + Api.VERSION_VALUE;
        }
        return super.getResult(signUrl);
    }
}
