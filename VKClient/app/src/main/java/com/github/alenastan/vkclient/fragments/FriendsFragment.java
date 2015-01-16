package com.github.alenastan.vkclient.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.alenastan.vkclient.Api;
import com.github.alenastan.vkclient.DetailsActivity;
import com.github.alenastan.vkclient.R;
import com.github.alenastan.vkclient.bo.Friend;
import com.github.alenastan.vkclient.bo.NoteGsonModel;
import com.github.alenastan.vkclient.helper.DataManager;
import com.github.alenastan.vkclient.oauth.VkOAuthHelper;
import com.github.alenastan.vkclient.process.BitmapProcessor;
import com.github.alenastan.vkclient.process.FriendArrayProcessor;
import com.github.alenastan.vkclient.source.CachedHttpDataSource;
import com.github.alenastan.vkclient.source.HttpDataSource;
import com.github.alenastan.vkclient.source.VkDataSource;

import java.util.List;

/**
 * Created by lena on 08.01.2015.
 */
public class FriendsFragment extends Fragment implements DataManager.Callback<List<Friend>> {

    public static final int LOADER_ID = 1;
    private static final String TAG = VkOAuthHelper.class.getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayAdapter mAdapter;
    private FriendArrayProcessor mFriendArrayProcessor = new FriendArrayProcessor();
    AbsListView mListView;
    TextView mError;
    private TextView mEmpty;
    private ProgressBar mProgressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container,false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        mListView = (AbsListView)root.findViewById(android.R.id.list);
        mProgressBar =(ProgressBar) root.findViewById(android.R.id.progress);
        mEmpty = (TextView)root.findViewById(android.R.id.empty);
        mError = (TextView)root.findViewById(R.id.error);
        return root;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // }

//    @Override
//    public void onResume() {
//        super.onResume();
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
        return new VkDataSource();
    }
    private void update(HttpDataSource dataSource, FriendArrayProcessor processor) {
        DataManager.loadData(this,
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
            mProgressBar.setVisibility(View.VISIBLE);
        }
        mEmpty.setVisibility(View.GONE);
    }


    private List<Friend> mData;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<Friend> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mProgressBar.setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        }

        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Friend>(getActivity().getApplicationContext(), R.layout.adapter_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getActivity().getApplicationContext(), R.layout.adapter_item, null);
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

                        }, url, CachedHttpDataSource.get(getActivity().getApplicationContext()), new BitmapProcessor());
                    }
                    return convertView;
                }

            };
            mListView.setAdapter(mAdapter);


            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
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
        mProgressBar.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.findViewById(R.id.error);
        mError.setVisibility(View.VISIBLE);
        mError.setText(mError.getText() + "\n" + e.getMessage());
    }


}