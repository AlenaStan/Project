package com.github.alenastan.taskmannager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by lena on 12.11.2014.
 */
public  class InitialContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout file that would normally be in the
        // ParentFragment at start
        View view = inflater.inflate(R.layout.layout_parentfragment,
                container, false);
        Bundle bundle = getArguments();
        final String text = bundle.getString("text");
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        textView.setText(text);
        Button button = (Button) view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentFragment parent = (ParentFragment) InitialContentFragment.this
                        .getParentFragment();
                parent.requestFragmentTransaction();
            }
        });
        return view;
    }
}
