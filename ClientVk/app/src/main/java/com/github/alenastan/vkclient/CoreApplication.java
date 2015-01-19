package com.github.alenastan.vkclient;

import android.app.Application;
import android.content.Context;

import com.github.alenastan.vkclient.datasourses.CachedHttpDataSource;
import com.github.alenastan.vkclient.datasourses.HttpDataSource;
import com.github.alenastan.vkclient.datasourses.VkDataSource;
import com.github.alenastan.vkclient.image.ImageLoader;

/**
 * Created by lena on 12.01.2015.
 */
public class CoreApplication extends Application {

    private HttpDataSource mHttpDataSource;
    private CachedHttpDataSource mCachedHttpDataSource;
    private VkDataSource mVkDataSource;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mImageLoader = new ImageLoader(this);
        mHttpDataSource = new HttpDataSource();
        mVkDataSource = new VkDataSource();
        mCachedHttpDataSource = new CachedHttpDataSource(this);
    }

    @Override
    public Object getSystemService(String name) {
        if (HttpDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mHttpDataSource == null) {
                mHttpDataSource = new HttpDataSource();
            }
            return mHttpDataSource;
        }
        if (CachedHttpDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mCachedHttpDataSource == null) {
                mCachedHttpDataSource = new CachedHttpDataSource(this);
            }
            return mCachedHttpDataSource;
        }
        if (VkDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mVkDataSource == null) {
                mVkDataSource = new VkDataSource();
            }
            return mVkDataSource;
        }
        if (ImageLoader.KEY.equals(name)) {
            //for android kitkat +
            if (mImageLoader == null) {
                mImageLoader = new ImageLoader(this);
            }
            return mImageLoader;
        }
        return super.getSystemService(name);
    }

    public static <T> T get(Context context, String key) {
        if (context == null || key == null){
            throw new IllegalArgumentException("Context and key must not be null");
        }
        T systemService = (T) context.getSystemService(key);
        if (systemService == null) {
            context = context.getApplicationContext();
            systemService = (T) context.getSystemService(key);
        }
        if (systemService == null) {
            throw new IllegalStateException(key + " not available");
        }
        return systemService;
    }
}