package com.github.alenastan.vkclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.github.alenastan.vkclient.utils.AuthUtils;

/**
 * Created by lena on 26.11.2014.
 */
public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_CODE_VK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AuthUtils.isLogged()) {
            startMainActivity();
        } else {
            startActivityForResult(new Intent(this, VkLoginActivity.class), REQUEST_CODE_VK);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VK && resultCode == RESULT_OK) {
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