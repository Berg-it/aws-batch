package com.bergit.aws.batch.servcies.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.bergit.aws.batch.model.CsvModel;
import com.bergit.aws.batch.servcies.FileReaderService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.commons.collections.comparators.FixedOrderComparator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class FileReaderServiceImpl implements FileReaderService {

    private static String pattern = "dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private static String bukcetName = "my-batch1-bucket";
    private static String fileName = "bank.csv";
    private static List<String> order = Arrays.asList("Account No", "DATE", "TRANSACTION DETAILS", "CHQ.NO.", "VALUE DATE", "WITHDRAWAL AMT", "DEPOSIT AMT", "BALANCE AMT", ".");
    private static String sourceFolder = simpleDateFormat.format(new Date());

    @Override
    public List<CsvModel> getFileData() {

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        S3Object s3Object;
        try {
            s3Object = s3Client.getObject(new GetObjectRequest(bukcetName + "/" + sourceFolder, fileName));
        } catch (Exception ex) {
            System.out.println("we cannot recover object from s3");
            throw new RuntimeException("we cannot recover object from s3");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.ISO_8859_1));
            final FixedOrderComparator comparator = new FixedOrderComparator(order);
            HeaderColumnNameMappingStrategy<CsvModel> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CsvModel.class);
            strategy.setColumnOrderOnWrite(comparator);

            CsvToBean csvToBean = new CsvToBeanBuilder(bufferedReader)
                    .withType(CsvModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build();
            return csvToBean.parse();
        } catch (Exception ex) {
            System.out.println("we cannot parse file");
            throw new RuntimeException("we cannot parse file");
        }

    }
}
