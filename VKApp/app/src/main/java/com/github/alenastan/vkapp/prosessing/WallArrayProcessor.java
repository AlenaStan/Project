package com.github.alenastan.vkapp.prosessing;

import com.github.alenastan.vkapp.model.Wall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lena on 25.01.2015.
 */
public class WallArrayProcessor implements Processor<List<Wall>,InputStream> {

    @Override
    public List<Wall> process(InputStream inputStream) throws Exception {
        String string = new StringProcessor().process(inputStream);
        JSONArray array = new JSONObject(string).getJSONObject("response").getJSONArray("items");
        List<Wall> noteArray = new ArrayList<Wall>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Wall wall = new Wall(jsonObject);
            // wall.initName();
            noteArray.add(wall);
        }
        return noteArray;
    }
}