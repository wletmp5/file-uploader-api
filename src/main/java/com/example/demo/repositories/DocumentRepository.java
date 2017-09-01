package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Document;

public interface DocumentRepository extends CrudRepository<Document, Long>, DocumentCustomRepository{
	
	public Document findDocumentByName(String name);
	
}
