package com.example.Practice.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String UPLOAD_DIR =
        System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        // Create uploads folder if not exists
        File directory = new File(UPLOAD_DIR);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save file
        String filePath =
                UPLOAD_DIR + file.getOriginalFilename();

        file.transferTo(new File(filePath));

        return ResponseEntity.ok(
                "File uploaded successfully: "
                        + file.getOriginalFilename()
        );
    }
    @GetMapping("/download/{fileName}")
public ResponseEntity<Resource> downloadFile(
        @PathVariable String fileName) {

    try {

        Path filePath = Paths.get(UPLOAD_DIR)
                .resolve(fileName)
                .normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                resource.getFilename() + "\"")
                .body(resource);

    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}

@GetMapping("/view/{fileName}")
public ResponseEntity<Resource> viewFile(
        @PathVariable String fileName) {

    try {

        Path filePath = Paths.get(UPLOAD_DIR)
                .resolve(fileName)
                .normalize();

        Resource resource =
                new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType =
                Files.probeContentType(filePath);

        return ResponseEntity.ok()

                .contentType(
                        MediaType.parseMediaType(contentType)
                )

                .body(resource);

    } catch (Exception e) {

        return ResponseEntity
                .internalServerError()
                .build();
    }
}
}