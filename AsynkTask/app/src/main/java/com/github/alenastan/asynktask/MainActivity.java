package com.github.alenastan.asynktask;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static TextView tvInfo;
    Button button;
    AsTask asTask;
    public static ProgressBar progress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        progress = (ProgressBar) findViewById(R.id.progress);
        tvInfo = (TextView) findViewById(R.id.tvInfo);


    }

    public void onDownloadClick(View view) {
        //new Thread(new Runnable() {
           // public void run() {
                asTask = new AsTask();
                asTask.execute();

            //}
       // }).start();
    }
}