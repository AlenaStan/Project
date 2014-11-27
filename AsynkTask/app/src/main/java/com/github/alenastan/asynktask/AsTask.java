package com.github.alenastan.asynktask;

import android.os.AsyncTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by lena on 24.10.2014.
 */
public class AsTask extends AsyncTask <Void, Integer, Void> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.tvInfo.setText("Start");
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            int counter = 0;

            for (int i = 0; i < 14; i++) {
                getIteration(counter);
                publishProgress(++counter);
            }

            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    protected void onPostExecute(Void result) {

        super.onPostExecute(result);
        MainActivity.tvInfo.setText("Done");
        MainActivity.progress.setProgress(0);
    }
    private void getIteration (int i) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        MainActivity.tvInfo.setText("Loading " + values[0]);
        MainActivity.progress.setProgress(values[0]);
    }
}
