package com.github.alenastan.downloadprogress.source;

import android.text.format.DateUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lena on 17.10.2014.
 */
public class DataSource {

    private static final List<String> DATA;

    static {
        DATA = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            DATA.add("test value " + i);
        }
    }

    public static List<String> getData() throws Exception {
        Thread.currentThread().sleep(DateUtils.SECOND_IN_MILLIS * 2);
        return DATA;
    }

}