package com.github.alenastan.vkapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alenastan.vkapp.MainActivity;
import com.github.alenastan.vkapp.R;

/**
 * Created by lena on 24.01.2015.
 */
public class FragmentNews extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static FragmentNews newInstance() {
        FragmentNews fragment = new FragmentNews();
        return fragment;
    }

    public FragmentNews() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container,
                false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(3);
    }

}
