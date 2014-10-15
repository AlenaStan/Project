package com.github.alenastan.launchmode;

/**
 * Created by lena on 15.10.2014.
 */
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SampleActivity extends Activity {

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