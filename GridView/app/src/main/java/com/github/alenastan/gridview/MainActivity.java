package com.github.alenastan.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Items> items = new ArrayList<Items>();
        items.add(new Items ("Значение #1", 22));
        items.add(new Items ("Значение #2", 66));
        items.add(new Items("Значение #3", 89));
        items.add(new Items ("Значение #4", 77));

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new Adapter( this,items ));
    }

}
