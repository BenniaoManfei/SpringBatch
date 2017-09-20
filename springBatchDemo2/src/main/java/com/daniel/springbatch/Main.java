package com.daniel.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.daniel.springbatch.model.bean.DeviceCommand;
import com.daniel.springbatch.model.bean.DeviceCommandLineAggregator;
import com.daniel.springbatch.model.bean.DeviceCommandLineMapper;
import com.daniel.springbatch.model.bean.Goods;
import com.daniel.springbatch.model.bean.Student;
import com.daniel.springbatch.processor.DeviceCommandProcessor;
import com.daniel.springbatch.processor.GoodsXMLProcessor;
import com.daniel.springbatch.processor.StudentFixedLengthProcessor;

public class Main {

	public static void main(String[] args) throws Exception {
//		test3();
		test4();
	}
	
	public static void test1() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo2Application.class);
		User user = ctx.getBean(User.class);
		System.err.println(user);
		
	}
	
	public void test2() throws Exception {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo2Application.class);
		User user = ctx.getBean(User.class);
		System.err.println(user);
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		FlatFileItemReader<DeviceCommand> reader = new FlatFileItemReader<>();
		reader.setResource( new FileSystemResource("src/main/resources/batch-data.csv"));
		reader.setLineMapper(new DeviceCommandLineMapper());
		
		//创建Processor
		DeviceCommandProcessor processor = new DeviceCommandProcessor();
		
		//创建writer
		FlatFileItemWriter<DeviceCommand> writer = new FlatFileItemWriter<>();
		writer.setResource( new FileSystemResource("src/main/resources/batch-result.csv"));
		writer.setLineAggregator(new DeviceCommandLineAggregator());
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step")
										.<DeviceCommand,DeviceCommand>chunk(1)
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
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo2Application.class);
		User user = ctx.getBean(User.class);
		System.err.println(user);
		
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		ItemReader<Goods> reader = ctx.getBean(ItemReader.class);
		
		//创建Processor
		GoodsXMLProcessor processor = new GoodsXMLProcessor();
		
		//创建writer
		ItemWriter<Goods> writer = ctx.getBean(ItemWriter.class);
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step")
										.<Goods,Goods>chunk(1)
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
		JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
		System.err.println(jobExecution);
	}
	
	public static void test4() throws Exception {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBatchDemo2Application.class);
		User user = ctx.getBean(User.class);
		System.err.println(user);
		
		//获取任务启动器
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobRepository jobRepository = ctx.getBean(JobRepository.class);
		PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);
		
		//创建reader
		FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src/main/resources/batch-data.txt"));
		DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
		FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
		fixedLengthTokenizer.setColumns(new Range[]{new Range(1, 6),new Range(7, 15),new Range(16, 18),new Range(19)});
		fixedLengthTokenizer.setNames(new String[]{"id","name","age","score"});
		lineMapper.setLineTokenizer(fixedLengthTokenizer);

		BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Student.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		reader.setLineMapper(lineMapper);
		
		//创建Processor
		StudentFixedLengthProcessor processor = new StudentFixedLengthProcessor();
		
		//创建writer
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("src/main/resources/batch-result.txt"));
		FormatterLineAggregator<Student> lineAggregator = new FormatterLineAggregator<>();
		BeanWrapperFieldExtractor<Student> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[]{"id","name","age","score"});
		lineAggregator.setFieldExtractor(fieldExtractor);
		lineAggregator.setFormat("%-9s%-20s%4d%-2.0f");
		writer.setLineAggregator(lineAggregator);
		
		//创建Step
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
		Step step = stepBuilderFactory.get("step")
										.<Student,Student>chunk(1)
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
		JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
		System.err.println(jobExecution);
	}
}
