package com.daniel.springbatch.batch;

import java.util.Random;

import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import com.daniel.springbatch.exceptions.MyRetryException;
import com.daniel.springbatch.exceptions.MySkipException;
import com.daniel.springbatch.model.pojo.Person;

public class PersonProcessor2 implements ItemProcessor<Person, Person> {

	private JdbcTemplate jdbcTemplate;
	
	private static final String GET_PRODUCT = "select id,address,age,name FROM person2 where id = ? ";
	
	Random random = new Random();
			
	
	@Override
	public Person process(Person person) throws Exception {
		System.err.println("----->开始处理:"+person);
		int rnum = random.nextInt(3);
		long rand =  (person.getId()) % 15;
		if(rand == 0L) {
			
			throw new MySkipException("整除20跳过:"+person.getId()+",随机数:"+rnum);
		} /*else if(rand == 0L) {
			System.err.println("----->>>>>>>>>>:"+person.getId()+",随机数:"+rnum);
			if(rnum==2) {
				throw new MyRetryException("整除5重试:"+person.getId()+",随机数:"+rnum);
			}
		}*/
		//整除5跳过
		//除5余4，加上随机数，重试
		person.setRemark(rnum);
		System.err.println("<-----处理结束:"+person);
		return person;
		
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
