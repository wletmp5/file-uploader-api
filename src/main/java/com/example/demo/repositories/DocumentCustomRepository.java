package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Document;

public interface DocumentCustomRepository {
	@Query("select id, name, size from Document d "
			+ "where (:name is null or :name='' or d.name = :name) and size > :size")
	public List<Document> findByDocumentByCriteria(@Param("name")String name, @Param("size") long size);
}
