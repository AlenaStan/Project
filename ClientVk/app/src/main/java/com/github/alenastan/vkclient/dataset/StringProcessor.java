package com.github.alenastan.vkclient.dataset;

import com.github.alenastan.vkclient.datasourses.HttpDataSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lena on 12.01.2015.
 */
public class StringProcessor implements Processor<String, InputStream> {
    @Override
    public String process(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = null;
        BufferedReader in = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream);
            in = new BufferedReader(inputStreamReader);
            String str;
            StringBuilder builder = new StringBuilder();
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            return builder.toString();
        } finally {
            HttpDataSource.close(in);
            HttpDataSource.close(inputStreamReader);
            HttpDataSource.close(inputStream);
        }
    }
}