package com.banvien.fc.file.service;

import com.banvien.fc.file.utils.ImageResizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    @Value("${file.location}")
    private String fileLocation;

    public void store(MultipartFile file, String filename, String path) {
//		String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path p = Paths.get(fileLocation + path);
        try {
            Files.createDirectories(p);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, p.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    public void resizeImage(String filename, String path) {
        try {
            ImageResizer.resize(fileLocation + path + File.separator + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource loadAsResource(String filename, String path) {
        try {
            Path p = Paths.get(fileLocation + path);
            Path file = p.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(fileLocation));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
