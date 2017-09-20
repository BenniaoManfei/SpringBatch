package com.daniel.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.daniel.springbatch.model.bean.Student;

public class StudentFixedLengthProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student student) throws Exception {
		System.err.println("----->开始处理:"+student);
		/* 合并ID和名字 */
		student.setName(student.getId() + "--" + student.getName());
		/* 年龄加2 */
		student.setAge(student.getAge() + 2);
		/* 分数加10 */
		student.setScore(student.getScore() + 10);
		/* 将处理后的结果传递给writer */
		System.err.println("<-----处理结束:"+student);
		return student;
	}

}
