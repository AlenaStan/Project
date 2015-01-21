package com.github.alenastan.vkclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by lena on 12.01.2015.
 */
public class StartActivity extends ActionBarActivity {

     public static final int REQUEST_LOGIN = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Session.getTokenFromSharedPreferences(this) == null) {
            startActivityForResult(new Intent(this, VkLoginActivity.class), REQUEST_LOGIN);
            } else {
            startMainActivity();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            Session mSession = Session.start();
            mSession.saveTokenToSharedPreferences(data.getStringExtra(Session.ACCESS_TOKEN),this);
            startMainActivity();
        } else {
            finish();
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
