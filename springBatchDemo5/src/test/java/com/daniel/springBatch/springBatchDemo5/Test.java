package com.daniel.springBatch.springBatchDemo5;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daniel.springBatch.model.pojo.CreditBill;

public class Test {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application.xml");
		CreditBill creditBill = ctx.getBean(CreditBill.class);
		System.err.println(creditBill);
		
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = ctx.getBean(Job.class);
		
		JobExecution result = jobLauncher.run(job, new JobParameters());
		System.err.println(result);
	}

}
