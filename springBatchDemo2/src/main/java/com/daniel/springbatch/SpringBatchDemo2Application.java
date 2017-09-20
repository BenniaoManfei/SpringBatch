package com.daniel.springbatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.daniel.springbatch.model.bean.Goods;

@SpringBootApplication
public class SpringBatchDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo2Application.class, args);
	}
	
	//事务管理器
	@Bean(name="transactionManager")
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}
	
	//任务仓库
	@Bean(name="jobRepository")
	public MapJobRepositoryFactoryBean jobRepository() {
		return  new MapJobRepositoryFactoryBean(transactionManager());
	}
	
	//任务加载器
	@Bean(name="jobLauncher")
	public SimpleJobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository().getObject());
		
		return jobLauncher;
	}
	
	//@Bean
	public ItemReader<Goods> itemReader(XStreamMarshaller streamMarshaller) {
		StaxEventItemReader<Goods> itemReader = new StaxEventItemReader<>();
		itemReader.setFragmentRootElementName("goods");
		itemReader.setUnmarshaller(streamMarshaller);
		itemReader.setResource(new FileSystemResource("src/main/resources/batch-data-xml.xml"));
	
		return itemReader;
	}
	
//	@Bean
	public ItemWriter<Goods> itemWriter(XStreamMarshaller streamMarshaller) {
		StaxEventItemWriter<Goods> itemWriter = new StaxEventItemWriter<>();
		itemWriter.setRootTagName("wanggc");
		itemWriter.setMarshaller(streamMarshaller);
		itemWriter.setResource(new FileSystemResource("src/main/resources/batch-result-xml.xml"));
	
		return itemWriter;
	}
	
//	@Bean
	public XStreamMarshaller streamMarshaller() {
		XStreamMarshaller streamMarshaller = new XStreamMarshaller();
		
		Map<String, Object> aliases = new HashMap<>();
		aliases.put("goods", "com.daniel.springbatch.model.bean.Goods");
		aliases.put("buyDay", "java.util.Date");
		
		streamMarshaller.setAliases(aliases);
		
		return streamMarshaller;
	}
	
	@Bean
	public User getUser() {
		User user = new User(1L,"Daniel");
		return user;
	}
}

