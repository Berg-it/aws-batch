package com.bergit.aws.batch;

import com.bergit.aws.batch.servcies.FileReaderService;
import com.bergit.aws.batch.servcies.FileReaderServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        FileReaderService readerService = new FileReaderServiceImpl();
        readerService.getFile();
        System.out.println( "Hello World!" );
    }
}
