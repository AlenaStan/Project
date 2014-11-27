package com.github.alenastan.vkclient.utils;

import com.github.alenastan.vkclient.auth.VkOAuthHelper;

/**
 * Created by lena on 26.11.2014.
 */
public class AuthUtils {
    public static boolean isLogged() {
        return VkOAuthHelper.isLogged();
    }
}
