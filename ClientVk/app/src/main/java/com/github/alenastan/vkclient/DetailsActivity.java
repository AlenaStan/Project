package com.github.alenastan.vkclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.github.alenastan.vkclient.vkentities.NoteGsonModel;

/**
 * Created by lena on 12.01.2015.
 */
public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        NoteGsonModel noteGsonModel = (NoteGsonModel) getIntent().getSerializableExtra("item");
        ((TextView)findViewById(android.R.id.text1)).setText(noteGsonModel.getTitle());
        ((TextView)findViewById(android.R.id.text2)).setText(noteGsonModel.getContent());
    }

}