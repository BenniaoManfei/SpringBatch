package com.daniel.springbatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.daniel.springbatch.model.pojo.Person;


public class PersonWriter implements ItemWriter<Person>  {

	 private JdbcTemplate jdbcTemplate;
		
	private static final String GET_PRODUCT = "SELECT id,address,age,name FROM person WHERE id = ? ";
	private static final String INSERT_PRODUCT = "INSERT INTO person2 (id,address,age,name,remark) VALUES (?,?,?,?,?)";
	private static final String UPDATE_PRODUCT = "UPDATE person SET address = ?, age = ?,name = ? WHERE id = ?";

	@Override
	public void write(List<? extends Person> items) throws Exception {
		for(Person person: items) {
			jdbcTemplate.update(INSERT_PRODUCT, person.getId(),person.getAddress(),person.getAge(),person.getName(),person.getRemark());
			
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
				jdbcTemplate.update(INSERT_PRODUCT, person.getAddress(),person.getAge(),person.getName());
			} else {
				jdbcTemplate.update(UPDATE_PRODUCT, person.getAddress(),person.getAge(),person.getName(),person.getId());
			}*/
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
