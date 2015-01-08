package com.example.lena.testscreen.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lena.testscreen.R;

/**
 * Created by lena on 30.12.2014.
 */
public class ScreenOne extends Fragment {

    public ScreenOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screen_first, container,
                false);

        return rootView;
    }

}
