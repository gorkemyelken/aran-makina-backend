package com.aranmakina.backend.controller;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileToFTP(@RequestBody FileUploadRequest request) {
        // Base64 encoded file içeriyor
        byte[] decodedFile = Base64Utils.decodeFromString(request.getFile());

        // FTP işlemleri burada yapılacak
        FTPClient ftpClient = new FTPClient();

        try {
            // FTP Bağlantısını kur
            ftpClient.connect("89.252.187.226", 21); // FTP sunucusunun adresini ve portunu gir
            ftpClient.login("arancara", "Gorkem123"); // FTP kullanıcı adı ve şifresi

            // FTP'ye yazma modunu ayarla
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            // Dosyayı FTP sunucusuna yükle
            boolean success = ftpClient.storeFile("/public_html/" + request.getFileName(), new ByteArrayInputStream(decodedFile));

            if (success) {
                return ResponseEntity.ok(new UploadResponse("Dosya başarıyla yüklendi."));
            } else {
                return ResponseEntity.status(500).body(new UploadResponse("Dosya yüklenirken hata oluştu."));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new UploadResponse("FTP bağlantısında bir hata oluştu."));
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class FileUploadRequest {
        private String file;
        private String fileName;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
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