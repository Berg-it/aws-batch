package com.bergit.aws.batch;

import com.bergit.aws.batch.servcies.FileReaderService;
import com.bergit.aws.batch.servcies.WriterService;
import com.bergit.aws.batch.servcies.impl.FileReaderServiceImpl;
import com.bergit.aws.batch.servcies.ProcessService;
import com.bergit.aws.batch.servcies.impl.ProcessServiceImpl;
import com.bergit.aws.batch.servcies.impl.WriterServiceImpl;

import java.util.List;

/**
 * Author: BERGUIGA Mohamed Amine
 * contact: m.a.berguiga.info@gmail.com
 */
public class App 
{
    public static void main( String[] args )
    {
        FileReaderService readerService = new FileReaderServiceImpl();
        ProcessService processService = new ProcessServiceImpl();
        final List fileData = readerService.getFileData();
        final List list = processService.processData(fileData);
        WriterService writerService = new WriterServiceImpl();
        writerService.writeToDynamoDb(list);

    }
}
