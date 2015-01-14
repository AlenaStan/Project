package com.github.alenastan.vkclient.source;

/**
 * Created by lena on 14.01.2015.
 */
public interface DataSource<Result,Params>{

    Result getResult(Params params) throws Exception;

}
