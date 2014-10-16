package com.github.alenastan.gridview;

/**
 * Created by lena on 16.10.2014.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.github.alenastan.gridview.Items;
import java.util.List;

public class Adapter extends BaseAdapter {
    private Context context;
    private List<Items> products;

    public Adapter (Context context, List<Items> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = new Button(context);
            button.setText(products.get(position).getName());
        } else {
            button = (Button) convertView;
        }
        button.setId(position);

        return button;
    }
}
