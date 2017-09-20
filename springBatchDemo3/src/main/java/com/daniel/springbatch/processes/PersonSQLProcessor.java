package com.daniel.springbatch.processes;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.daniel.springbatch.model.po.Person;

public class PersonSQLProcessor implements ItemProcessor<Person, Person> {
	private static final String GET_PRODUCT = "select id,address,age,name FROM person where id = ? ";

	
	@Override
	public Person process(Person person) throws Exception {
		System.err.println("----->开始处理:"+person);
		/*List<Person> persons = jdbcTemplate.query(GET_PRODUCT,new Object[]{person.getId()},new RowMapper<Person>(){

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
			
		} else {
			Person person
		}*/
		person.setAge(person.getAge()+1);
		System.err.println("<-----处理结束:"+person);
		return person;
	}

}
