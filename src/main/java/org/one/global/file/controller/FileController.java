package org.one.global.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.one.global.apiPayload.ApiResponse;
import org.one.global.file.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File", description = "관리자 파일 업로드/삭제")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @Operation(summary = "파일 업로드")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) {
        String path = storageService.store(file);
        return ResponseEntity.ok(ApiResponse.success(path));
    }

    @Operation(summary = "파일 삭제")
    @DeleteMapping("")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam("path") String path) {
        storageService.delete(path);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
