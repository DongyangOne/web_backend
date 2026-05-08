package org.one.global.file.controller;

import org.one.global.apiPayload.ApiResponse;
import org.one.global.file.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) {
        String path = storageService.store(file);
        return ResponseEntity.ok(ApiResponse.success(path));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam("path") String path) {
        storageService.delete(path);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
