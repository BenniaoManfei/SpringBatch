package com.daniel.springbatch;

import java.util.Arrays;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.resource.ListPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.daniel.springbatch.batch.PersonProcessor;
import com.daniel.springbatch.batch.PersonWriter;
import com.daniel.springbatch.exceptions.MySkipException;
import com.daniel.springbatch.model.pojo.Person;

public class Main {

	public static void main(String[] args) throws Exception {
		test();
	}
	
	public static void test () throws Exception {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo4Application.class);
	
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(ctx.getBean(DataSource.class));
		reader.setVerifyCursorPosition(true);
		reader.setSql(" SELECT id,address,age,name FROM person WHERE id > ? ");
		
		BeanPropertyRowMapper<Person> rowMapper = new BeanPropertyRowMapper<>();
		rowMapper.setMappedClass(Person.class);
		reader.setRowMapper(rowMapper);
		
		ListPreparedStatementSetter preparedStatementSetter = new ListPreparedStatementSetter();
		preparedStatementSetter.setParameters(Arrays.asList(1L));
		reader.setPreparedStatementSetter(preparedStatementSetter);
		
		//创建Processor
		PersonProcessor processor = new PersonProcessor();
		processor.setJdbcTemplate(ctx.getBean(JdbcTemplate.class));
		
		//创建writer
		PersonWriter writer = new PersonWriter();
		writer.setJdbcTemplate(ctx.getBean(JdbcTemplate.class));
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step2")
										.<Person,Person>chunk(1)
										.reader(reader)
										.processor(processor)
										.writer(writer)
										.faultTolerant()
//										.retry(Exception.class)//设置重试机制
//										.noRetry(MySQLDataException。c)//不重试
//										.retryLimit(1)//每条记录重试一次
//										.listener(new RetryFailuireItemListener())设置监听
										.skip(MySkipException.class)//跳过
										.skipLimit(5)
										.taskExecutor(new SimpleAsyncTaskExecutor())//设置并发方式执行
										.throttleLimit(4)//并发任务为10，默认为4
										.build();
		
		//创建Job
		JobParameters jobParameters = new JobParametersBuilder()  
                .addDate("date", new Date())       
                .toJobParameters(); 
		JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
		Job job = jobBuilderFactory.get("job2")
									.incrementer(new RunIdIncrementer())
									.flow(step)
									.end()
									.build();
		
		//启动任务
		jobLauncher.run(job, jobParameters);
	}
	
	
	
}
