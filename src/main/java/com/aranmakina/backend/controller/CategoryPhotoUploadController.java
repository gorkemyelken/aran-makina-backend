package com.aranmakina.backend.controller;

import com.aranmakina.backend.service.CategoryService;
import com.aranmakina.backend.service.ProductService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/categoryPhotoUpload")
@CrossOrigin
public class CategoryPhotoUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileToFTP(@RequestParam("file") MultipartFile file,
                                             @RequestParam("categoryId") Integer categoryId) {
        FTPClient ftpClient = new FTPClient();

        logger.info("Dosya yükleme işlemi başlatıldı.");

        try {
            // FTP Bağlantısını kur
            logger.info("FTP sunucusuna bağlanıyor...");
            ftpClient.connect("89.252.187.226", 21); // FTP sunucusunun adresini ve portunu gir
            ftpClient.login("arancara", "Gorkem123"); // FTP kullanıcı adı ve şifresi
            logger.info("FTP sunucusuna bağlanıldı.");

            // FTP'ye yazma modunu ayarla
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            logger.info("FTP sunucusunda dosya yazma modu ayarlandı.");

            // Dosyayı FTP sunucusuna yükle
            String remoteFilePath = "/httpdocs/images/category/" + file.getOriginalFilename();
            logger.info("Yükleme işlemi başlıyor: " + file.getOriginalFilename());
            boolean success = ftpClient.storeFile(remoteFilePath, file.getInputStream());

            if (success) {
                logger.info("Dosya başarıyla yüklendi.");

                // Fotoğraf URL'si ile ürünü güncelle
                String photoUrl = "https://arancaraskal.com/images/category/" + file.getOriginalFilename();
                boolean isUpdated = categoryService.addCategoryPhoto(categoryId, photoUrl);

                if (isUpdated) {
                    return ResponseEntity.ok(new FileUploadController.UploadResponse("Dosya başarıyla yüklendi ve ürünle ilişkilendirildi."));
                } else {
                    return ResponseEntity.status(500).body(new FileUploadController.UploadResponse("Fotoğraf ürüne eklenirken hata oluştu."));
                }
            } else {
                logger.error("Dosya yüklenirken hata oluştu.");
                return ResponseEntity.status(500).body(new FileUploadController.UploadResponse("Dosya yüklenirken hata oluştu."));
            }
        } catch (IOException e) {
            logger.error("FTP bağlantısında bir hata oluştu: " + e.getMessage(), e);
            return ResponseEntity.status(500).body(new FileUploadController.UploadResponse("FTP bağlantısında bir hata oluştu."));
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                logger.info("FTP bağlantısı kapatıldı.");
            } catch (IOException e) {
                logger.error("FTP bağlantısı kapatılırken bir hata oluştu: " + e.getMessage(), e);
            }
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
