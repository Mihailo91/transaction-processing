package com.example.batchprocessing.config;

import com.example.batchprocessing.model.Transaction;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionPreparedStatementSetter implements ItemPreparedStatementSetter<Transaction> {

    @Override
    public void setValues(Transaction item, PreparedStatement ps) throws SQLException {
        ps.setLong(1, item.getId());
        ps.setString(2, item.getCardHolder());
        ps.setString(3, item.getCardNumber());
        ps.setBigDecimal(4, item.getAmount() );
        ps.setTimestamp(5, Timestamp.from(item.getTimeOfTransaction()));
        ps.setBigDecimal(6, item.isSuspiciousActivity() ? BigDecimal.valueOf(1) : BigDecimal.valueOf(0));
    }
}