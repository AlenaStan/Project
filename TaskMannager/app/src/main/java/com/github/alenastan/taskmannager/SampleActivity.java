package com.github.alenastan.taskmannager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by lena on 11.10.2014.
 */
public class SampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }

    public void onReturnHomeClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onStartSampleClick(View view) {
        startActivity(new Intent(this, SampleActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}