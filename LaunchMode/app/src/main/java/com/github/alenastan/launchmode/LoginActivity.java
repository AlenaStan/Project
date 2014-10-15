package com.github.alenastan.launchmode;

/**
 * Created by lena on 15.10.2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.github.alenastan.launchmode.utils.AuthUtils;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
    }

    public void onLoginClick(View view) {
        AuthUtils.setLogged(true);
        setResult(RESULT_OK);
        finish();
    }
    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}


