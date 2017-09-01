package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Document {
	
	@SuppressWarnings("unused")
	private Document () {}

	public Document(String name, long size) {
		this.name = name;
		this.size = size;
	}

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private long size;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
}
