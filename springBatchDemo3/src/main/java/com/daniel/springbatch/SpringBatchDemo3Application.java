package com.daniel.springbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootApplication
public class SpringBatchDemo3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo3Application.class, args);
	}

	// 事务管理器
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// 任务仓库
	@Bean(name = "jobRepository")
	public MapJobRepositoryFactoryBean jobRepository(PlatformTransactionManager transactionManager) {
		return new MapJobRepositoryFactoryBean(transactionManager);
	}

	// 任务加载器
	@Bean(name = "jobLauncher")
	public JobLauncher jobLauncher(MapJobRepositoryFactoryBean jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository.getObject());
		return jobLauncher;
	}
	
	@Bean
	public JobExplorer jobExplorer() {
		return new SimpleJobExplorer(null,null,null,null);
	}
	
	@Bean
	public JobRegistry jobLocator() {
		return new MapJobRegistry();
	}
	
	@Bean
	public JobOperator jobOperator(JobRegistry jobLocator,JobLauncher jobLauncher,MapJobRepositoryFactoryBean jobRepository ,JobExplorer jobExplorer) throws Exception {
		SimpleJobOperator jobOperator = new SimpleJobOperator();
		jobOperator.setJobRegistry(jobLocator);
		jobOperator.setJobLauncher(jobLauncher);
		jobOperator.setJobRepository(jobRepository.getObject());
		jobOperator.setJobExplorer(jobExplorer);
		return jobOperator;
	}
	
	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
