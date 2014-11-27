package com.github.alenastan.vkclient.source;

/**
 * Created by lena on 26.11.2014.
 */
public interface DataSource<Result,Params>{

    Result getResult(Params params) throws Exception;

}