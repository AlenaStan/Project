package com.example.lena.testscreen;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lena.testscreen.bo.Friend;
import com.example.lena.testscreen.bo.NoteGsonModel;
import com.example.lena.testscreen.helper.DataManager;
import com.example.lena.testscreen.processing.BitmapProcessor;
import com.example.lena.testscreen.processing.FriendArrayProcessor;
import com.example.lena.testscreen.source.CachedHttpDataSource;
import com.example.lena.testscreen.source.HttpDataSource;
import com.example.lena.testscreen.source.VkDataSource;

import java.util.List;

/**
 * Created by lena on 09.01.2015.
 */
public class FriendsActivity extends ActionBarActivity implements DataManager.Callback<List<Friend>> {

    public static final int LOADER_ID = 0;

    private ArrayAdapter mAdapter;

    private FriendArrayProcessor mFriendArrayProcessor = new FriendArrayProcessor();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_friends);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = getHttpDataSource();
        final FriendArrayProcessor processor = getProcessor();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(dataSource, processor);
            }
        });
        update(dataSource, processor);
    }

    private FriendArrayProcessor getProcessor() {
        return mFriendArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return VkDataSource.get(FriendsActivity.this);
    }

    private void update(HttpDataSource dataSource, FriendArrayProcessor processor) {
        DataManager.loadData(FriendsActivity.this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.FRIENDS_GET;
    }

    @Override
    public void onDataLoadStart() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        }
        findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

    private List<Friend> mData;


    @Override
    public void onDone(List<Friend> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
        }
        AdapterView listView = (AbsListView) findViewById(android.R.id.list);
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Friend>(this, R.layout.adapter_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(FriendsActivity.this, R.layout.adapter_item, null);
                    }
                    Friend item = getItem(position);
                    TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);
                    textView1.setText(item.getName());
                    TextView textView2 = (TextView) convertView.findViewById(android.R.id.text2);
                    textView2.setText(item.getNickname());
                    convertView.setTag(item.getId());
                    final ImageView imageView = (ImageView) convertView.findViewById(android.R.id.icon);
                    final String url = item.getPhoto();
                    imageView.setImageBitmap(null);
                    imageView.setTag(url);
                    if (!TextUtils.isEmpty(url)) {
                        //TODO add delay and cancel old request or create limited queue
                        //TODO create sync Map to check existing request and existing callbacks
                        //TODO create separate thread pool for manager
                        DataManager.loadData(new DataManager.Callback<Bitmap>() {
                            @Override
                            public void onDataLoadStart() {

                            }

                            @Override
                            public void onDone(Bitmap bitmap) {
                                if (url.equals(imageView.getTag())) {
                                    imageView.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }

                        }, url, CachedHttpDataSource.get(FriendsActivity
                                .this), new BitmapProcessor());
                    }
                    return convertView;
                }

            };
            listView.setAdapter(mAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FriendsActivity.this, DetailsActivity.class);
                    Friend item = (Friend) mAdapter.getItem(position);
                    NoteGsonModel note = new NoteGsonModel(item.getId(), item.getFirstName(), item.getLastName());
                    intent.putExtra("item", note);
                    startActivity(intent);
                }
            });
        } else {
            mData.clear();
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        findViewById(android.R.id.empty).setVisibility(View.GONE);
        TextView errorView = (TextView) findViewById(R.id.error);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

}
