package com.github.alenastan.vkapp.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.alenastan.vkapp.Api;
import com.github.alenastan.vkapp.DetailsActivity;
import com.github.alenastan.vkapp.MainActivity;
import com.github.alenastan.vkapp.R;
import com.github.alenastan.vkapp.helper.DataManager;
import com.github.alenastan.vkapp.image.ImageLoader;
import com.github.alenastan.vkapp.model.Friend;
import com.github.alenastan.vkapp.model.NoteGsonModel;
import com.github.alenastan.vkapp.prosessing.FriendArrayProcessor;
import com.github.alenastan.vkapp.sourse.HttpDataSource;
import com.github.alenastan.vkapp.sourse.VkDataSource;

import java.util.List;

/**
 * Created by lena on 24.01.2015.
 */
public class FragmentFriends extends Fragment implements DataManager.Callback<List<Friend>> {



    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayAdapter mAdapter;
    private FriendArrayProcessor mFriendArrayProcessor = new FriendArrayProcessor();
    private AbsListView mListView;
    private TextView mError;
    private TextView mEmpty;
    private ProgressBar mProgressBar;
    private ImageLoader mImageLoader;



    public FragmentFriends() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container,false);
        mImageLoader = ImageLoader.get(getActivity().getApplicationContext());
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
        return Api.FRIENDS_GET ;
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
        //ListView listView = (ListView) findViewById(android.R.id.list);


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
                    final String url = item.getPhoto();
                    final ImageView imageView = (ImageView) convertView.findViewById(android.R.id.icon);
                    mImageLoader.loadAndDisplay(url, imageView);
                    return convertView;
                }

            };

            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
                    Friend item = (Friend) mAdapter.getItem(position);
                    NoteGsonModel note = new NoteGsonModel(item.getId(), item.getFirstName(), item.getLastName(),item.getPhoto());
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
        //TextView errorView = (TextView) findViewById(R.id.error);
        mError.setVisibility(View.VISIBLE);
        mError.setText(mError.getText() + "\n" + e.getMessage());
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }

}
