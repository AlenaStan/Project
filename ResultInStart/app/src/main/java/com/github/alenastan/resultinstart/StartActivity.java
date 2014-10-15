package com.github.alenastan.resultinstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.github.alenastan.resultinstart.utils.AuthUtils;

/**
 * Created by lena on 14.10.2014.
 */
public class StartActivity extends Activity {

    public static final int REQUEST_LOGIN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AuthUtils.isLogged()) {
            startMainActivity();
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
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
