package com.github.alenastan.vkapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import com.github.alenastan.vkapp.autorization.EncryptManager;
import com.github.alenastan.vkapp.autorization.VkOAuthHelper;

/**
 * Created by lena on 25.01.2015.
 */
public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_LOGIN = 0;
    public static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            startActivityForResult(new Intent(this, VkLoginActivity.class), REQUEST_LOGIN);
        } else {
            startMainActivity();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            if (data.hasExtra(VkOAuthHelper.TOKEN)) {
                String token = data.getStringExtra(VkOAuthHelper.TOKEN);
                saveToken(token);
                startMainActivity();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveToken(String token) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (TextUtils.isEmpty(token)) {
            prefEditor.remove(VkOAuthHelper.TOKEN);
        } else {
            try {
                token = EncryptManager.encrypt(this, token);
                prefEditor.putString(VkOAuthHelper.TOKEN, token);
            } catch (Exception e) {
                e.printStackTrace();
                prefEditor.remove(VkOAuthHelper.TOKEN);
            }
        }
        prefEditor.apply();
    }

    private String getToken() {
        String token = PreferenceManager.getDefaultSharedPreferences(this).getString(VkOAuthHelper.TOKEN, "");
        if (!TextUtils.isEmpty(token))
            try {
                token = EncryptManager.decrypt(this, token);
                VkOAuthHelper.sToken = token;
            } catch (Exception e) {
                e.printStackTrace();
                token = "";
            }
        return token;
    }
}