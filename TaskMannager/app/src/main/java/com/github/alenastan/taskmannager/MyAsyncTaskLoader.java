package com.github.alenastan.taskmannager;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by lena on 29.10.2014.
 */
public class MyAsyncTaskLoader extends AsyncTaskLoader<String> {


    final int PAUSE = 10;

    public final static String ARGS_TIME_FORMAT = "time_format";
    public final static String TIME_FORMAT_SHORT = "h:mm:ss a";
    public final static String TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss";

    String format;

    public  MyAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        if (args != null)
            format = args.getString(ARGS_TIME_FORMAT);
        if (TextUtils.isEmpty(format))
            format = TIME_FORMAT_SHORT;
    }

    @Override
    public String loadInBackground() {

        try {
            TimeUnit.SECONDS.sleep(PAUSE);
        } catch (InterruptedException e) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date());
    }

}