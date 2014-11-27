package com.github.alenastan.taskmannager.processing;

import com.github.alenastan.taskmannager.source.HttpDataSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Created by lena on 24.10.2014.
 */
public class RedirectProcessor<DataSourceResult> implements Processor<DataSourceResult, DataSourceResult> {
    @Override
    public DataSourceResult process(DataSourceResult dataSourceResult) throws Exception {
        return dataSourceResult;
    }
}