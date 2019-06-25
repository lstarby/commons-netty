package com.jf.crawl.demo;

import com.jf.crawl.codec.msg.DefaultMessage;

public class PeopleMessage extends DefaultMessage {

	private String name;
	
	private Integer age;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
