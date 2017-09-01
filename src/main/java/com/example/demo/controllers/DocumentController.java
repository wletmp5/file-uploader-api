package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.DocumentUploadException;
import com.example.demo.models.Document;
import com.example.demo.services.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentController {
	
	private final DocumentService documentService;
	
	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Document> executeUpload(
    		@RequestParam(value="file", required=true) MultipartFile file) throws DocumentUploadException{
		return ResponseEntity.ok(documentService.saveFile(file));
	}
	
	@RequestMapping(value="/{id}/content", method=RequestMethod.GET)
    public ResponseEntity<byte[]> getFileContents(
    		@PathVariable(name="id", required=true) long id) throws DocumentUploadException{
		return ResponseEntity.ok(documentService.getFileContent(id));
	}
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Document>> getAllDocuments(
    		@PathParam(value="name") Optional<String> name, 
    		@PathParam(value="size") Optional<Long> size){
    			return ResponseEntity.ok(documentService.getDocumentsByCriteria(name.orElse(""), size.orElse(0L)));
	}
    
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public ResponseEntity<Document> getDocumentById(
    		@PathVariable(name="id", required=true) long id) throws DocumentUploadException{
		return ResponseEntity.ok(documentService.getDocumentById(id));
	}
    
	
}
