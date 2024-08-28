
package com.example.batchprocessing.helper;
import com.example.batchprocessing.model.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) {
        Transaction transaction = new Transaction();
        transaction.setId(fieldSet.readInt("id"));
        transaction.setCardHolder(fieldSet.readString("cardHolder"));
        transaction.setCardNumber(fieldSet.readString("cardNumber"));
        transaction.setAmount(fieldSet.readBigDecimal("amount"));

        String timeOfTransactionString = fieldSet.readString("timeOfTransaction");
        LocalDateTime localDateTime = LocalDateTime.parse(timeOfTransactionString, DATE_TIME_FORMATTER);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        transaction.setTimeOfTransaction(instant);

        transaction.setSuspiciousActivity(fieldSet.readBoolean("suspiciousActivity"));
        return transaction;
    }
}
