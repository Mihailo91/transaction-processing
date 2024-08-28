package com.example.batchprocessing.helper;

import com.example.batchprocessing.model.Transaction;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class TransactionLineMapper {

    private  TransactionLineMapper() {
        throw new IllegalStateException("Can't initialize bean");
    }

    public static LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "cardHolder", "cardNumber", "amount", "timeOfTransaction", "suspiciousActivity");

        TransactionFieldSetMapper fieldSetMapper = new TransactionFieldSetMapper();

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
