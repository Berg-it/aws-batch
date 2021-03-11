package com.bergit.aws.batch.servcies;

import com.bergit.aws.batch.model.CsvModel;

import java.util.List;

public interface WriterService {

    void writeToDynamoDb(List<CsvModel> list);

}
