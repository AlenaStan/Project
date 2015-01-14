package com.github.alenastan.vkclient.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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


    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container,false);

        return root;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = getHttpDataSource();
        final FriendArrayProcessor processor = getProcessor();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                update(dataSource, processor);
            }

        }); update(dataSource, processor);


    }
    private FriendArrayProcessor getProcessor() {
        return mFriendArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return VkDataSource.get(getActivity());
    }
    private void update(HttpDataSource dataSource, FriendArrayProcessor processor) {
        DataManager.loadData(FriendsFragment.this,
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
            getActivity().findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        }
        getActivity().findViewById(android.R.id.empty).setVisibility(View.GONE);
    }


    private List<Friend> mData;


    @Override
    public void onDone(List<Friend> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        getActivity().findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            getActivity().findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
        }
        AdapterView listView = (AbsListView) getActivity().findViewById(android.R.id.list);
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Friend>(getActivity(), R.layout.adapter_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getContext(), R.layout.adapter_item, null);
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

                        }, url, CachedHttpDataSource.get(getContext()), new BitmapProcessor());
                    }
                    return convertView;
                }

            };
            listView.setAdapter(mAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
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
        getActivity().findViewById(android.R.id.progress).setVisibility(View.GONE);
        getActivity().findViewById(android.R.id.empty).setVisibility(View.GONE);
        TextView errorView = (TextView) getActivity().findViewById(R.id.error);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

//        AdapterView listView = (AbsListView) getActivity().findViewById(android.R.id.list);
//        mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_item, android.R.id.text1, Friends.NAMES);
//
//
//
//        setListAdapter(mAdapter);
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//           @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),
//                        "Click ListItem Number " + position , Toast.LENGTH_LONG)
//                        .show();
//                         showDetails(position);
//
//
//            }
//        });
//
//    }
//    void showDetails(int index) {
//        getListView().setItemChecked(index, true);
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), DetailActivity.class);
//        intent.putExtra("index", index);
//        startActivity(intent);
//    }



}