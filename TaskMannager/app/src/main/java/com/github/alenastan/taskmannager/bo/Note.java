package com.github.alenastan.taskmannager.bo;

import org.json.JSONObject;
import java.io.Serializable;


/**
 * Created by lena on 24.10.2014.
 */
public class Note extends JSONObjectWrapper {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String ID = "id";

    public Note(String jsonObject) {
        super(jsonObject);
    }

    public Note(JSONObject jsonObject) {
        super(jsonObject);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public String getContent() {
        return getString(CONTENT);
    }

    public Long getId() {
        return getLong(ID);
    }

}