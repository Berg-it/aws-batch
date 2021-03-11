package com.bergit.aws.batch;

import com.bergit.aws.batch.servcies.FileReaderService;
import com.bergit.aws.batch.servcies.FileReaderServiceImpl;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        FileReaderService readerService = new FileReaderServiceImpl();
        final List fileData = readerService.getFileData();
        System.out.println( "Hello World!" );
    }
}
