package com.github.alenastan.vkclient.datasourses;

/**
 * Created by lena on 12.01.2015.
 */
public interface DataSource<Result,Params>{

    Result getResult(Params params) throws Exception;

}