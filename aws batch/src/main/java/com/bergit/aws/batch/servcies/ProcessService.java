package com.bergit.aws.batch.servcies;

import com.bergit.aws.batch.model.CsvModel;

import java.util.List;

public interface ProcessService {

    List<CsvModel> processData(List<CsvModel> input);

}
