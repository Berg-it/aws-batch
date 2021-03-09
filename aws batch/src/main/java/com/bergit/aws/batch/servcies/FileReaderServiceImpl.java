package com.bergit.aws.batch.servcies;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {


    @Override
    public void getFile() {

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        List<Bucket> buckets=s3Client.listBuckets();
        for(Bucket b:buckets){
            System.out.println("Bucket Name equal to: "+b.getName());
        }


    }
}
