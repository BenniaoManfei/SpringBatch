package com.daniel.springbatch.processor;

import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.daniel.springbatch.model.bean.Goods;

public class GoodsXMLProcessor implements ItemProcessor<Goods, Goods> {

	@Override
	public Goods process(Goods goods) throws Exception {
		goods.setBuyDay(new Date());
		goods.setCustomer(goods.getCustomer()+"-顾客");
		goods.setIsin("ISBN-"+goods.getIsin());
		goods.setPrice(goods.getPrice()+100.00);
		goods.setQuantity(goods.getQuantity()-1);
		return goods;
	}

}
