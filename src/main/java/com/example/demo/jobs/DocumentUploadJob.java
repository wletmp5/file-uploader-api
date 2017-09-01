package com.example.demo.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.services.DocumentService;

@Component
public class DocumentUploadJob {

	private final DocumentService documentService;

	@Autowired
	public DocumentUploadJob(DocumentService documentService) {
		this.documentService = documentService;
	}

	@Value("${job.directory.path}")
	private String directory;

	@Value("${job.file.lastModifiedDate}")
	private long lastModifiedDate;

	@Scheduled(fixedRate = 10000)
	public void reportCurrentTime() throws Exception {
		uploadFile();
	}

	public void uploadFile() throws Exception {

		for (File file : retrieveFiles(directory, lastModifiedDate)) {
			if (!documentService.isDocumentExists(file.getName())) {
				documentService.saveFile(new MockMultipartFile("file", file.getName(), "text/plain",
						IOUtils.toByteArray(new FileInputStream(file))));
			}
		}

	}

	private static List<File> retrieveFiles(String path, long lastMofification) throws IOException {
		return Files.list(Paths.get(path)).filter(Files::isRegularFile)
				.filter(file -> file.toFile().lastModified() > (System.currentTimeMillis() - lastMofification))
				.map(file -> file.toFile()).collect(Collectors.toList());
	}

}
