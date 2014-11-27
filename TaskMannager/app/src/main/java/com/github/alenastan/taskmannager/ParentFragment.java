package com.github.alenastan.taskmannager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by lena on 12.11.2014.
 */
public class ParentFragment extends Fragment {

    private static final int CONTAINER_ID = 0x2222;
    private static final String INITIAL_FRAG = "initial_fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout wrapper = new FrameLayout(getActivity());
        wrapper.setId(CONTAINER_ID);
        // look for our two possible fragments, if we don't find the
        // InitialContentFragment add it
        if (getChildFragmentManager().findFragmentByTag(INITIAL_FRAG) == null) {
            InitialContentFragment initContent = new InitialContentFragment();
            Bundle args = new Bundle();
            args.putString("text",
                    "I'm the initial content fragment in the parent fragment");
            initContent.setArguments(args);
            getChildFragmentManager().beginTransaction()
                    .add(CONTAINER_ID, initContent, INITIAL_FRAG).commit();
        }
        return wrapper;
    }

    public void requestFragmentTransaction() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        ChildFragment childFragment = new ChildFragment();
        Bundle args = new Bundle();
        args.putString("text", "Hi I am Child Fragment");
        childFragment.setArguments(args);
        fragmentTransaction.replace(CONTAINER_ID, childFragment, "ChildFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}