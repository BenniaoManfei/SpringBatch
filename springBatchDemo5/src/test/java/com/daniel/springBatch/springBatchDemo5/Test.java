package com.daniel.springBatch.springBatchDemo5;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daniel.springBatch.model.pojo.CreditBill;

/**
 * CSV文件的读取和写入
 *
 * @description
 *
 * @author DaiZM
 * @date 2017年9月21日
 *
 */
public class Test {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-batch.xml");
		CreditBill creditBill = ctx.getBean(CreditBill.class);
		System.err.println(creditBill);
		
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = ctx.getBean(Job.class);
		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("outputFilePath", "data-bill-2017-09-result.csv")
				.toJobParameters();
		JobExecution result = jobLauncher.run(job, jobParameters);
		System.err.println(result);
	}

}
