package com.github.alenastan.taskmannager.processing;

/**
 * Created by lena on 24.10.2014.
 */
public interface Processor<ProcessingResult, Source> {

    ProcessingResult process(Source source) throws Exception;

}