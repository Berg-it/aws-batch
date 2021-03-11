package com.bergit.aws.batch.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvModel {

    @CsvBindByPosition(position = 0)
    private String accountNum;

    @CsvBindByPosition(position = 1)
    private String date;

    @CsvBindByPosition(position = 2)
    private String transactionDetails;

    @CsvBindByPosition(position = 3)
    private String chqNum;

    @CsvBindByPosition(position = 4)
    private String valueDate;

    @CsvBindByPosition(position = 5)
    private String withDrawalAmt;

    @CsvBindByPosition(position = 6)
    private String depositAmt;

    @CsvBindByPosition(position = 7)
    private String balanceAmt;

    @CsvBindByPosition(position = 8)
    private String lastField;



}
