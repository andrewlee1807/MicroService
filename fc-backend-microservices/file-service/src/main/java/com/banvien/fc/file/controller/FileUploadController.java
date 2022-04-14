package com.banvien.fc.file.controller;

import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.file.service.StorageFileNotFoundException;
import com.banvien.fc.file.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping("/file")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename, "");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(value = "/image/{outletId}/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> imageFile(@PathVariable String filename,
                                              @PathVariable("outletId") Long outletId) {
        String path = File.separator + "image" + File.separator + outletId;
        Resource file = storageService.loadAsResource(filename, path);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping("/upload/image")
    @ResponseBody
    public ResponseEntity handleImageUpload(@RequestParam("file") MultipartFile file,
                                            HttpServletRequest request) {
        Long outletId = RestUtil.getOutletIdFromToken(request);
        if (outletId == null) {
            outletId = 0l;
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        filename = String.valueOf(System.currentTimeMillis()) + filename.substring(filename.lastIndexOf('.'));
        String path = File.separator + "image" + File.separator + outletId;
        storageService.store(file, filename, path);
        if (file.getSize() > 1024 * 1024) {
            storageService.resizeImage(filename, path);
        }
        return ResponseEntity.ok("\"" + path.replace(File.separator, "/") + "/" + filename + "\"");
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file, StringUtils.cleanPath(file.getOriginalFilename()), "");
        return ResponseEntity.ok(StringUtils.cleanPath(file.getOriginalFilename()));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
