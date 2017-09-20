package com.daniel.springbatch.batch;

import org.springframework.batch.core.SkipListener;

import com.daniel.springbatch.model.pojo.Person;

public class PersonSkipListener implements SkipListener<Person, Person> {

	@Override
	public void onSkipInRead(Throwable t) {
		System.err.println("READ====================>");
		t.printStackTrace();
	}

	@Override
	public void onSkipInWrite(Person item, Throwable t) {
		System.err.println("READ====================>"+item.toString());
		t.printStackTrace();
	}

	@Override
	public void onSkipInProcess(Person item, Throwable t) {
		System.err.println("PROCESS====================>"+item.toString());
		t.printStackTrace();
	}

}
