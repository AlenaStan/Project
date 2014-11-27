package com.github.alenastan.taskmannager.callbacks;

import android.util.Log;
import com.github.alenastan.taskmannager.helper.DataManager;

/**
 * Created by lena on 24.10.2014.
 */
public abstract class SimpleCallback<Result> implements DataManager.Callback {

    @Override
    public void onDataLoadStart() {
        Log.d("SimpleCallback", "onDataLoadStart");
    }

    @Override
    public void onError(Exception e) {
        Log.e("SimpleCallback", "onError", e);
    }

}