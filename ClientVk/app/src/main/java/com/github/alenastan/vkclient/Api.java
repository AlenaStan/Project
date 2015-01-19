package com.github.alenastan.vkclient;

/**
 * Created by lena on 12.01.2015.
 */
public class Api {

    public static final String BASE_PATH = "https://api.vk.com/method/";
    public static final String VERSION_VALUE = "5.8";
    public static final String VERSION_PARAM = "v";

    public static final String FRIENDS_GET = BASE_PATH + "friends.get?fields=photo_200_orig,online,nickname";
    public static final String WALL_GET = BASE_PATH + "wall.get?filters=owner&fields=photo_100&extended=1";
}