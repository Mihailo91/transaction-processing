package com.example.batchprocessing.config;

import javax.sql.DataSource;

import com.example.batchprocessing.helper.TransactionLineMapper;
import com.example.batchprocessing.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@Configuration
public class BatchConfiguration {

	@Bean
	public FlatFileItemReader<Transaction> reader() {
		return new FlatFileItemReaderBuilder<Transaction>()
				.name("personItemReader")
				.resource(new ClassPathResource("transactions.csv"))
				.linesToSkip(1)
				.lineMapper(TransactionLineMapper.lineMapper())
				.delimited()
				.names("id", "cardHolder", "cardNumber", "amount", "timeOfTransaction", "suspiciousActivity")
				.targetType(Transaction.class)
				.build();
	}

	@Bean
	public TransactionItemProcessor processor() {
		return new TransactionItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {

		return new JdbcBatchItemWriterBuilder<Transaction>()
				.sql("INSERT INTO transaction (id, card_holder, card_number, amount, time_of_transaction, suspicious_activity) " +
						" VALUES (?, ?, ?, ?, ?, ? )")
				.itemPreparedStatementSetter(new TransactionPreparedStatementSetter())
				.dataSource(dataSource)
				.beanMapped()
				.build();
	}

	@Bean
	public Job importUserJob(JobRepository jobRepository,Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepository)
			.listener(listener)
			.start(step1)
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
					  FlatFileItemReader<Transaction> reader, TransactionItemProcessor processor, JdbcBatchItemWriter<Transaction> writer) {
		return new StepBuilder("step1", jobRepository)
			.<Transaction, Transaction> chunk(3, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}
}
