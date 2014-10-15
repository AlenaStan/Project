package com.github.alenastan.launchmode.utils;

/**
 * Created by lena on 15.10.2014.
 */
public class AuthUtils {

    private static boolean IS_AUTHORIZED = false;

    public static void setLogged(boolean isLogged) {

        IS_AUTHORIZED = isLogged;
    }

    public static boolean isLogged() {
        return IS_AUTHORIZED;
    }
}