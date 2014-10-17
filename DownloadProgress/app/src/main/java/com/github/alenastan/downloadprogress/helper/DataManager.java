package com.github.alenastan.downloadprogress.helper;

import android.os.Handler;
import com.github.alenastan.downloadprogress.source.DataSource;
import java.util.List;

/**
 * Created by lena on 17.10.2014.
 */
public class DataManager {

    public static interface Callback {
        void onDataLoadStart();
        void onDone(List<String> data);
        void onError(Exception e);
    }

    public static void loadData(final Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
        final Handler handler = new Handler();
        callback.onDataLoadStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> data = DataSource.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDone(data);
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

}