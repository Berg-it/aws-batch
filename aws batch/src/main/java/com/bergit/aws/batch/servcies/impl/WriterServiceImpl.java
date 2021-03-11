package com.bergit.aws.batch.servcies.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.bergit.aws.batch.model.CsvModel;
import com.bergit.aws.batch.servcies.WriterService;

import java.util.List;

public class WriterServiceImpl implements WriterService {

    private static String tableDynamoDbName = "bank-transaction-proc";
    private static String hashKeyName = "Account_No";

    @Override
    public void writeToDynamoDb(List<CsvModel> list) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableDynamoDbName);

        for(CsvModel element :list){
            Item item = new Item()
                            .withPrimaryKey(hashKeyName, element.getAccountNum())
                            .withString("transactionDetails", element.getTransactionDetails())
                            .withString("transactionDetails", element.getTransactionDetails())
                            .withString("valueDate", element.getValueDate())
                            ;
            PutItemOutcome outcome = table.putItem(item);
        }

    }

}
