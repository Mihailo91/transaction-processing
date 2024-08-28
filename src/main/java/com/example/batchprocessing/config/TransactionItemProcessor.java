package com.example.batchprocessing.config;

import com.example.batchprocessing.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction> {

	private static final Logger log = LoggerFactory.getLogger(TransactionItemProcessor.class);

	private static final BigDecimal MAX_VALUE = new BigDecimal("10000.00");

	@Override
	public Transaction process(Transaction item) throws Exception {
		if(item.getAmount().compareTo(MAX_VALUE) >= 0) {
			item.setSuspiciousActivity(true);
			log.info("Suspicious transaction flagged: {}", item);
		}

		return item;
	}

}
