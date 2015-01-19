package com.github.alenastan.vkclient.dataset;

/**
 * Created by lena on 12.01.2015.
 */
public interface Processor<ProcessingResult, Source> {

    ProcessingResult process(Source source) throws Exception;

}