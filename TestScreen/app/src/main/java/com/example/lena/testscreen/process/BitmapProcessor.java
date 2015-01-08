package com.example.lena.testscreen.process;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lena.testscreen.source.HttpDataSource;

import java.io.InputStream;

/**
 * Created by lena on 26.11.2014.
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