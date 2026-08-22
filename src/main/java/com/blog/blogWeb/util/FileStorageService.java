package com.blog.blogWeb.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogWeb.exception.FileSotreException;

@Service
public class FileStorageService {
	
	private final String UPLOAD_DIR = "uploads/";
	
	public String saveFile(MultipartFile file) throws FileSotreException {
		if (file == null || file.isEmpty()) {
            return null;
        }
		try {
		Path uploadPath = Paths.get(UPLOAD_DIR);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path filePath = uploadPath.resolve(fileName);
		
		Files.copy(file.getInputStream(), filePath);
		return filePath.toString();
		}catch (Exception e) {
			throw new FileSotreException(e.getMessage());		}
	}

}
