package com.github.alenastan.vkclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.github.alenastan.vkclient.oauth.EncodeManager;
import com.github.alenastan.vkclient.oauth.VkOAuthHelper;

/**
 * Created by lena on 04.01.2015.
 */
public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_CODE_VK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mToken = tokenFromSharedPreferences(VkOAuthHelper.ACCESS_TOKEN);
        if (!TextUtils.isEmpty(mToken)) {
            startMainActivity(mToken);
        } else {
            startActivityForResult(new Intent(this, VkLoginActivity.class), REQUEST_CODE_VK);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VK && resultCode == RESULT_OK && data.hasExtra(VkOAuthHelper.ACCESS_TOKEN)) {
            String token = data.getStringExtra(VkOAuthHelper.ACCESS_TOKEN);
            saveTokenToSharedPreferences(token);
            startMainActivity(token);
        } else {
            finish();
        }
    }

    private void startMainActivity(String mToken) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(VkOAuthHelper.ACCESS_TOKEN, mToken);
        startActivity(intent);
        //finish();
    }
    private String tokenFromSharedPreferences(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sToken = prefs.getString(key,"");
        if(prefs.contains(key))
            try {

                sToken = EncodeManager.decode(this, sToken);
                Toast.makeText(this, sToken, Toast.LENGTH_SHORT).show();

            }catch(Exception e){
                e.printStackTrace();

            }
        return sToken;
    }

    public void saveTokenToSharedPreferences(String accessToken) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        if (TextUtils.isEmpty(accessToken)) {
            edit.remove(VkOAuthHelper.ACCESS_TOKEN);
        } else {

            try {
                accessToken = EncodeManager.encode(this, accessToken);
                edit.putString(VkOAuthHelper.ACCESS_TOKEN, accessToken);
                edit.commit();
                Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        edit.apply();
    }
}