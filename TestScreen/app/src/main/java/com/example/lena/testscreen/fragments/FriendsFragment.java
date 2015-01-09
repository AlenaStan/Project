package com.example.lena.testscreen.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lena.testscreen.R;

/**
 * Created by lena on 08.01.2015.
 */
public class FriendsFragment  extends Fragment {

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_friends, container,
                false);

        return rootView;
    }

}
