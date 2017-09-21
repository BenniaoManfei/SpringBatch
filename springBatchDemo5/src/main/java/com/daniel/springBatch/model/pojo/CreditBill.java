package com.daniel.springBatch.model.pojo;

import java.io.Serializable;

public class CreditBill implements Serializable {

	private Long id;
	
	private String accountID = "";
	private String name = "";
	private double account = 0.0;
	private String date = null;
	private String address = "";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "CreditBill [accountID=" + accountID + ", name=" + name + ", account=" + account + ", date=" + date + ", address=" + address + "]";
	}
	
	
}
