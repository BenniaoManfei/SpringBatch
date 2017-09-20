package com.daniel.springbatch;


import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.resource.ListPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.daniel.springbatch.model.po.Person;
import com.daniel.springbatch.processes.PersonSQLProcessor;
import com.daniel.springbatch.writer.PersonSQLWriter;


public class Main {

	public static void main(String[] args) {
		try {
			test3();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void test1() throws Exception {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo3Application.class);
	
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
		reader.setResource( new FileSystemResource("src/main/resources/person-data.csv"));
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[]{"id","address","age","name"});
		lineMapper.setLineTokenizer(lineTokenizer);
		reader.setLineMapper(lineMapper);
		
		BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Person.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		reader.setLineMapper(lineMapper);
		//创建Processor
		PersonSQLProcessor processor = new PersonSQLProcessor();
		
		//创建writer
		PersonSQLWriter itemWriter = new PersonSQLWriter();
		itemWriter.setJdbcTemplate(ctx.getBean(JdbcTemplate.class));
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step")
										.<Person,Person>chunk(1)
										.reader(reader)
										.processor(processor)
										.writer(itemWriter)
										.build();
		
		//创建Job
		JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
		Job job = jobBuilderFactory.get("job")
									.start(step)
									.build();
		
		//启动任务
		jobLauncher.run(job, new JobParameters());
	}
	
	public static void test2() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo3Application.class);
	
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(ctx.getBean(DataSource.class));
		reader.setSql(" SELECT id,address,age,name FROM person WHERE id > ? ");
		
		BeanPropertyRowMapper<Person> rowMapper = new BeanPropertyRowMapper<>();
		rowMapper.setMappedClass(Person.class);
		reader.setRowMapper(rowMapper);
		
		ListPreparedStatementSetter preparedStatementSetter = new ListPreparedStatementSetter();
		preparedStatementSetter.setParameters(Arrays.asList(2L));
		reader.setPreparedStatementSetter(preparedStatementSetter);
		
		//创建Processor
		PersonSQLProcessor processor = new PersonSQLProcessor();
		
		//创建writer
		//创建writer
		FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();
		writer.setResource( new FileSystemResource("src/main/resources/batch-result.csv"));
		DelimitedLineAggregator<Person> lineAggregator = new DelimitedLineAggregator<>();
		writer.setLineAggregator(lineAggregator);
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step")
										.<Person,Person>chunk(1)
										.reader(reader)
										.processor(processor)
										.writer(writer)
										.build();
		
		//创建Job
		JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
		Job job = jobBuilderFactory.get("job")
									.start(step)
									.build();
		
		//启动任务
		jobLauncher.run(job, new JobParameters());
	}
	
	public static void test3() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo3Application.class);
	
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(ctx.getBean(DataSource.class));
		reader.setSql(" SELECT id,address,age,name FROM person WHERE id > ? ");
		
		BeanPropertyRowMapper<Person> rowMapper = new BeanPropertyRowMapper<>();
		rowMapper.setMappedClass(Person.class);
		reader.setRowMapper(rowMapper);
		
		ListPreparedStatementSetter preparedStatementSetter = new ListPreparedStatementSetter();
		preparedStatementSetter.setParameters(Arrays.asList(2L));
		reader.setPreparedStatementSetter(preparedStatementSetter);
		
		//创建Processor
		PersonSQLProcessor processor = new PersonSQLProcessor();
		
		//创建writer
		PersonSQLWriter writer = new PersonSQLWriter();
		writer.setJdbcTemplate(ctx.getBean(JdbcTemplate.class));
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step1")
										.<Person,Person>chunk(1)
										.reader(reader)
										.processor(processor)
										.writer(writer)
										.faultTolerant()
//										.retry(Exception.class)//设置重试机制
//										.noRetry(MySQLDataException。c)//不重试
//										.retryLimit(1)//每条记录重试一次
//										.listener(new RetryFailuireItemListener())设置监听
										.skip(Exception.class)//跳过
										.skipLimit(100)
										.taskExecutor(new SimpleAsyncTaskExecutor())//设置并发方式执行
										.throttleLimit(10)//并发任务为10，默认为4
										.build();
		
		//创建Job
		JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
		Job job = jobBuilderFactory.get("job1")
									.start(step)
									.build();
		
		//启动任务
		jobLauncher.run(job, new JobParameters());
	}
}

