package com.github.alenastan.vkclient.process;

/**
 * Created by lena on 14.01.2015.
 */
public interface Processor<ProcessingResult, Source> {

    ProcessingResult process(Source source) throws Exception;

}