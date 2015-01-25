package com.github.alenastan.vkapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.alenastan.vkapp.autorization.VkOAuthHelper;

/**
 * Created by lena on 25.01.2015.
 */
public class VkLoginActivity extends ActionBarActivity implements VkOAuthHelper.Callbacks{

private static final String TAG = VkLoginActivity.class.getSimpleName();
private WebView mWebView;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk_login);

        getSupportActionBar().hide();
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebViewClient(new MWebViewClient());
        mWebView.loadUrl(Api.AUTORIZATION_URL);
        }

@Override
public void onError(Exception e) {
        new AlertDialog.Builder(this)
        .setTitle("Authorisation error")
        .setMessage(e.getMessage())
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
        setResult(RESULT_CANCELED);
        finish();
        }
        })
        .create()
        .show();
        }

@Override
public void onSuccess(String token) {
        Intent intent = getIntent();
        intent.putExtra(VkOAuthHelper.TOKEN, token);
        setResult(RESULT_OK, intent);
        finish();
        }

private class MWebViewClient extends WebViewClient {

    public MWebViewClient() {
        super();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.d(TAG, "page started " + url);
        showProgress();
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "override " + url);
        if (VkOAuthHelper.proceedRedirectURL(VkLoginActivity.this, url, VkLoginActivity.this)) {
            Log.d(TAG, "override redirect");
            view.setVisibility(View.INVISIBLE);
            Log.d(TAG, "Parsing url " + url);
            setResult(RESULT_OK);
            finish();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.setVisibility(View.VISIBLE);
        dismissProgress();
        Log.d(TAG, "error " + failingUrl);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG, "finish " + url);
        view.setVisibility(View.VISIBLE);
        dismissProgress();
    }
}
    private void dismissProgress() {
        findViewById(android.R.id.progress).setVisibility(View.GONE);
    }

    private void showProgress() {
        findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
    }
}
