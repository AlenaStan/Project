package com.github.alenastan.taskmannager;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by lena on 28.10.2014.
 */
public class LoadActivity extends ActionBarActivity implements LoaderCallbacks<String> {


    static final int LOADER_TIME_ID = 1;

    TextView tvTime;
    RadioGroup rgStringFormat;
    static int lastCheckedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        tvTime = (TextView) findViewById(R.id.tvTime);
        rgStringFormat = (RadioGroup) findViewById(R.id.rgTimeFormat);

        Bundle bndl = new Bundle();
        bndl.putString(MyAsyncTaskLoader.ARGS_TIME_FORMAT, getTimeFormat());
        getLoaderManager().initLoader(LOADER_TIME_ID, bndl, this);
        lastCheckedId = rgStringFormat.getCheckedRadioButtonId();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> loader = null;
        if (id == LOADER_TIME_ID) {
            loader = new MyAsyncTaskLoader(this, args);

        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String result) {
        tvTime.setText(result);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    public void getTimeClick(View v) {
        Loader<String> loader;

        int id = rgStringFormat.getCheckedRadioButtonId();
        if (id == lastCheckedId) {
            loader = getLoaderManager().getLoader(LOADER_TIME_ID);
        } else {
            Bundle bndl = new Bundle();
            bndl.putString(MyAsyncTaskLoader.ARGS_TIME_FORMAT, getTimeFormat());
            loader = getLoaderManager().restartLoader(LOADER_TIME_ID, bndl,
                    this);
            lastCheckedId = id;
        }
        loader.forceLoad();
    }

    String getTimeFormat() {
        String result = MyAsyncTaskLoader.TIME_FORMAT_SHORT;
        switch (rgStringFormat.getCheckedRadioButtonId()) {
            case R.id.rdShort:
                result = MyAsyncTaskLoader.TIME_FORMAT_SHORT;
                break;
            case R.id.rdLong:
                result = MyAsyncTaskLoader.TIME_FORMAT_LONG;
                break;
        }
        return result;
    }

}