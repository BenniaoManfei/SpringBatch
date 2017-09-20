package com.daniel.springbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class SpringBatchConfiger {

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
	
	// 事务管理器
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// 任务仓库
	@Bean(name = "jobRepository")
	public JobRepositoryFactoryBean jobRepository(DataSource dataSource,PlatformTransactionManager transactionManager) {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		return jobRepositoryFactoryBean;
	}

	// 任务加载器
	@Bean(name = "jobLauncher")
	public JobLauncher jobLauncher(JobRepositoryFactoryBean jobRepository) throws Exception {
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
	public JobOperator jobOperator(JobRegistry jobLocator,JobLauncher jobLauncher,JobRepositoryFactoryBean jobRepository ,JobExplorer jobExplorer) throws Exception {
		SimpleJobOperator jobOperator = new SimpleJobOperator();
		jobOperator.setJobRegistry(jobLocator);
		jobOperator.setJobLauncher(jobLauncher);
		jobOperator.setJobRepository(jobRepository.getObject());
		jobOperator.setJobExplorer(jobExplorer);
		return jobOperator;
	}
	

	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
