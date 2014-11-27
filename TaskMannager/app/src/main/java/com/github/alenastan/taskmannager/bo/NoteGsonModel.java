package com.github.alenastan.taskmannager.bo;

import java.io.Serializable;

/**
 * Created by lena on 24.10.2014.
 */

public class NoteGsonModel implements Serializable {

    private String title;

    private String content;

    private Long id;

    public NoteGsonModel(Long id, String title, String content) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}