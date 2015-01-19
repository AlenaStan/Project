package com.github.alenastan.vkclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.alenastan.vkclient.helper.DataManager;
import com.github.alenastan.vkclient.image.ImageLoader;
import com.github.alenastan.vkclient.vkentities.Friend;
import com.github.alenastan.vkclient.vkentities.NoteGsonModel;

import java.util.List;

/**
 * Created by lena on 12.01.2015.
 */
public class DetailsActivity extends ActionBarActivity implements DataManager.Callback<List<Friend>>{


    private ImageLoader mImageLoader;
    NoteGsonModel noteGsonModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mImageLoader = ImageLoader.get(DetailsActivity.this);
        NoteGsonModel noteGsonModel = (NoteGsonModel) getIntent().getSerializableExtra("item");
        ((TextView)findViewById(android.R.id.text1)).setText(noteGsonModel.getTitle());
        ((TextView)findViewById(android.R.id.text2)).setText(noteGsonModel.getContent());
        ImageView imageView = (ImageView)findViewById(android.R.id.icon);
        mImageLoader.loadAndDisplay(noteGsonModel.getUrl(), imageView);

    }
    @Override
    public void onDataLoadStart() {
//        if (!mSwipeRefreshLayout.isRefreshing()) {
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
//        mEmpty.setVisibility(View.GONE);
    }
    @Override
    public void onError(Exception e) {
        e.printStackTrace();
//        mProgressBar.setVisibility(View.GONE);
//        mEmpty.setVisibility(View.GONE);
//        //TextView errorView = (TextView) findViewById(R.id.error);
//        mError.setVisibility(View.VISIBLE);
//        mError.setText(mError.getText() + "\n" + e.getMessage());
    }
    @Override
    public void onDone(List<Friend> data) {
//        if (mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
//        mProgressBar.setVisibility(View.GONE);
//        if (data == null || data.isEmpty()) {
//            mEmpty.setVisibility(View.VISIBLE);
        }


}