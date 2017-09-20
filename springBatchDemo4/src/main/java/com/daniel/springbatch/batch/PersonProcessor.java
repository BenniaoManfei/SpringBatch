package com.daniel.springbatch.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.daniel.springbatch.exceptions.MySkipException;
import com.daniel.springbatch.model.pojo.Person;

public class PersonProcessor implements ItemProcessor<Person, Person> {
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String GET_PRODUCT = "select id,address,age,name FROM person2 where id = ? ";
	
	@Override
	public Person process(Person person) throws Exception {
		System.err.println("----->开始处理:"+person);
		List<Person> persons = jdbcTemplate.query(GET_PRODUCT,new Object[]{person.getId()},new RowMapper<Person>(){
			@Override
			public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				Person p = new Person();
                p.setId( resultSet.getLong( 1 ) );
                p.setAddress( resultSet.getString( 2 ) );
                p.setAge( resultSet.getInt( 3 ) );
                p.setName( resultSet.getString( 4 ) );
                return p;
			}
		});
		
		if(persons == null || persons.size() ==0) {
			person.setName(person.getName()+"-from person");
			return person;
		} else {
			throw new MySkipException("Person2表中已经存在改数据:"+person.toString());
		}
/*		person.setAge(person.getAge()+1);
		System.err.println("<-----处理结束:"+person);
		return person;*/
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

}
