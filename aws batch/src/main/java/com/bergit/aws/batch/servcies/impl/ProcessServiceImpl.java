package com.bergit.aws.batch.servcies.impl;

import com.bergit.aws.batch.model.CsvModel;
import com.bergit.aws.batch.servcies.ProcessService;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessServiceImpl implements ProcessService {


    @Override
    public List<CsvModel> processData(List<CsvModel> input) {

        return input.stream().filter(el -> el.getTransactionDetails().startsWith("VISA")).collect(Collectors.toList());

    }

}
