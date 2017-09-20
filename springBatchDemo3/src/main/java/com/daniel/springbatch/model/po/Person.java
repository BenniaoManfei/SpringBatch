package com.daniel.springbatch.model.po;

import java.io.Serializable;

public class Person implements Serializable {

	private Long id;
	private String address;
	private Integer age;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", address=" + address + ", age=" + age + ", name=" + name + "]";
	}
	
	
}
