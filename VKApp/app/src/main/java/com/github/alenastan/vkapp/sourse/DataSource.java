package com.github.alenastan.vkapp.sourse;

/**
 * Created by lena on 25.01.2015.
 */
public interface DataSource<Result,Params>{

    Result getResult(Params params) throws Exception;

}