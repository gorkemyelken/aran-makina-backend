package com.aranmakina.backend.controller;

import com.aranmakina.backend.config.FTPHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Geçici dosya olarak kaydet
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Güzel.net FTP bilgileri
            String server = "89.252.187.226";
            int port = 21;
            String user = "arancara";
            String pass = "Gorkem123";
            String remoteDir = "/img/product";

            // Dosyayı FTP'ye yükle
            FTPHelper.uploadFile(server, port, user, pass, remoteDir, tempFile);

            // Geçici dosyayı sil
            tempFile.delete();

            return ResponseEntity.ok("Dosya başarıyla yüklendi.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Dosya yüklenirken bir hata oluştu.");
        }
    }
}
