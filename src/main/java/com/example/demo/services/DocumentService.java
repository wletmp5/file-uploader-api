package com.example.demo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.DocumentUploadException;
import com.example.demo.models.Document;
import com.example.demo.repositories.DocumentRepository;

@Service
public class DocumentService {

	private final DocumentRepository documentRepository;
	
	@Value("${document.upload.directory}")
	private String uploadDirectory;

	@Autowired
	public DocumentService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	public void uploadFile(MultipartFile file) throws DocumentUploadException {
		try {
			IOUtils.copy(file.getInputStream(), new FileOutputStream(new File(uploadDirectory + "/" + file.getOriginalFilename())));
		} catch (Exception e) {
			throw new DocumentUploadException("Error occurred while saving " + file.getOriginalFilename());
		}

	}

	public byte[] getFileContent(String file) throws DocumentUploadException {
		byte[] contents = null;
		try {
			contents = IOUtils.toByteArray(new FileInputStream(new File(file)));
		} catch (FileNotFoundException e) {
			throw new DocumentUploadException("File not found: " + file);
		} catch (IOException e) {
			throw new DocumentUploadException("Error occurred while reading " + file);
		}
		return contents;

	}

	public byte[] getFileContent(long id) throws DocumentUploadException {
		return getFileContent(documentRepository.findOne(id).getName());
	}

	public boolean isDocumentExists(String name) {
		return (documentRepository.findDocumentByName(name) != null) ? true : false;
	}

	public List<Document> getAllDocuments() {
		return (List<Document>) documentRepository.findAll();
	}

	public List<Document> getDocumentsByCriteria(String name, long size) {
		return documentRepository.findByDocumentByCriteria(name, size);
	}

	public Document getDocumentById(long id) throws DocumentUploadException {
		return documentRepository.findOne(id);
	}

	public Document getDocumentByName(String name) {
		return documentRepository.findDocumentByName(name);
	}

	public Document saveFile(MultipartFile file) throws DocumentUploadException {
		if (file.isEmpty())
			throw new DocumentUploadException("Uploaded file is empty!");
		if (isDocumentExists(file.getOriginalFilename()))
			throw new DocumentUploadException("File/Filename already exists!");
		uploadFile(file);
		return documentRepository.save(new Document(file.getOriginalFilename(), file.getSize()));
	}

}
