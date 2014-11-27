package com.github.alenastan.taskmannager.source;

/**
 * Created by lena on 24.10.2014.
 */
public interface DataSource<Result,Params>{

    Result getResult(Params params) throws Exception;

}