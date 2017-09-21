package com.daniel.springBatch.dao.mapper;

import org.apache.ibatis.annotations.Insert;

import com.daniel.springBatch.model.pojo.CreditBill;

public interface CreditBillMapper {

	@Insert("INSERT INTO tb_credit_bill (accountID,name,account,date,address) VALUES (#{accountID},#{name},#{account},#{date},#{address})")
	public void insert(CreditBill creditBill);
	
}
