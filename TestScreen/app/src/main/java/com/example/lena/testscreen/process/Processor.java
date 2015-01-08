package com.example.lena.testscreen.process;

/**
 * Created by lena on 26.11.2014.
 */
public interface Processor<ProcessingResult, Source> {

    ProcessingResult process(Source source) throws Exception;

}