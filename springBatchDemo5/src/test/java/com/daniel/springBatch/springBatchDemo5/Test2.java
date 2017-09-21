package com.daniel.springBatch.springBatchDemo5;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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

import com.daniel.springBatch.dao.mapper.CreditBillMapper;
import com.daniel.springBatch.model.pojo.CreditBill;

/**
 * mySQL数据库的读取和写入
 *
 * @description
 *
 * @author DaiZM
 * @date 2017年9月21日
 *
 */
public class Test2 {
	
	
	
	public static void main(String[] args) {
		try {
			test2();
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}
	
	public static void test2() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Long begin = System.currentTimeMillis();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-batch-mysql.xml");
		CreditBill creditBill = ctx.getBean(CreditBill.class);
		System.err.println(creditBill);
		
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = ctx.getBean(Job.class);
		JobParameters jobParameters = new JobParametersBuilder()
				.addDate("date", new Date())
				.toJobParameters();
		JobExecution result = jobLauncher.run(job, jobParameters);
		System.err.println(result);
		Long end = System.currentTimeMillis();
		System.err.println("总耗时:"+(end-begin));
	}
	
	/**
	 * 测试数据库插入
	 * 
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobRestartException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws JobParametersInvalidException
	 *
	 * @author DaiZM
	 * @date 2017年9月21日
	 *
	 */
	public static void test() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-batch-mysql.xml");
		CreditBill creditBill = ctx.getBean(CreditBill.class);
		System.err.println(creditBill);
		
		SqlSessionFactory sqlSessionFactory = ctx.getBean(SqlSessionFactory.class);
		System.err.println(sqlSessionFactory);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		CreditBillMapper mapper = sqlSession.getMapper(CreditBillMapper.class);
		System.err.println(mapper);
		CreditBill bill = new CreditBill();
		bill.setAccountID("4047390012345679");
		bill.setName("Daniel");
		bill.setAccount(2056.01);
		bill.setDate("2013-2-28 20:34:19");
		bill.setAddress("wu bei wuhan ");
		mapper.insert(bill);
		sqlSession.commit();
		sqlSession.close();
		/*JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = ctx.getBean(Job.class);
		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("outputFilePath", "data-bill-2017-09-result.csv")
				.toJobParameters();
		JobExecution result = jobLauncher.run(job, jobParameters);
		System.err.println(result);*/
	}
}
