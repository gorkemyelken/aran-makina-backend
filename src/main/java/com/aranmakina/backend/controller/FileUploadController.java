package com.aranmakina.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Dosya yükleme işlemi
            String filePath = "/img/product/" + file.getOriginalFilename();
            // Dosyayı belirli bir dizine kaydetme işlemi
            file.transferTo(new File(filePath));

            return ResponseEntity.ok(new UploadResponse("Dosya başarıyla yüklendi."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new UploadResponse("Dosya yüklenirken bir hata oluştu."));
        }
    }

    public static class UploadResponse {
        private String message;

        public UploadResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
