package com.github.alenastan.taskmannager.processing;

import com.github.alenastan.taskmannager.bo.User;

import org.json.JSONObject;

/**
 * Created by lena on 24.10.2014.
 */
public class UserArrayProcessor extends WrapperArrayProcessor<User> {

    @Override
    protected User createObject(JSONObject jsonObject) {
        return new User(jsonObject);
    }

}