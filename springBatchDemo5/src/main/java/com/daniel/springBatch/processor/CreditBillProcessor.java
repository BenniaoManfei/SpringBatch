package com.daniel.springBatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.daniel.springBatch.model.pojo.CreditBill;

public class CreditBillProcessor implements ItemProcessor<CreditBill, CreditBill> {

	public CreditBill process(CreditBill creditBill) throws Exception {
		System.err.println("----------------->开始处理:");
		System.err.println(creditBill);
		System.err.println("<-----------------处理结束");
		return creditBill;
	}

}
