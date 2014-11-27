package com.github.alenastan.taskmannager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lena on 12.11.2014.
 */
public class ChildFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(R.layout.layout_childfragment, container,
                    false);

            Bundle bundle = getArguments();
            final String text = bundle.getString("text");

            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
