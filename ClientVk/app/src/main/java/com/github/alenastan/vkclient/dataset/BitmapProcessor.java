package com.github.alenastan.vkclient.dataset;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.alenastan.vkclient.datasourses.HttpDataSource;

import java.io.InputStream;

/**
 * Created by lena on 12.01.2015.
 */
public class BitmapProcessor implements Processor<Bitmap, InputStream> {

    @Override
    public Bitmap process(InputStream inputStream) throws Exception {
        try {
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            HttpDataSource.close(inputStream);
        }
    }

}