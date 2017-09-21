package com.daniel.springBatch.writer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.item.ItemWriter;

import com.daniel.springBatch.dao.mapper.CreditBillMapper;
import com.daniel.springBatch.model.pojo.CreditBill;

public class CreditBillDBWriter implements ItemWriter<CreditBill> {

	public static final String INSERT = " INSERT INTO tb_credit_bill (accountID,name,account,date,address) VALUES (?,?,?,?,?) ";
	
	private SqlSessionFactory sqlSessionFactory;
	
	public void write(List<? extends CreditBill> items) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CreditBillMapper mapper = sqlSession.getMapper(CreditBillMapper.class);
		items.forEach(mapper::insert);
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	
}
