package com.github.alenastan.vkapp.prosessing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.alenastan.vkapp.sourse.HttpDataSource;

import java.io.InputStream;

/**
 * Created by lena on 25.01.2015.
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