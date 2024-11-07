package com.aranmakina.backend.service;

import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.ErrorDataResult;
import com.aranmakina.backend.exception.results.SuccessDataResult;
import com.aranmakina.backend.model.Product;
import com.aranmakina.backend.model.ProductPhoto;
import com.aranmakina.backend.repository.ProductPhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductPhotoService {

    private final ProductPhotoRepository productPhotoRepository;

    @Value("${product.photo.upload-dir}")
    private String uploadDir;

    public ProductPhotoService(ProductPhotoRepository productPhotoRepository) {
        this.productPhotoRepository = productPhotoRepository;
    }

    public DataResult<ProductPhoto> savePhoto(MultipartFile file, Product product) {
        try {
            // Dosya adı oluşturma
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Dosyayı sunucuya kaydetme
            Files.copy(file.getInputStream(), filePath);

            // ProductPhoto nesnesi oluşturma ve kaydetme
            ProductPhoto productPhoto = new ProductPhoto();
            productPhoto.setUrl("/img/product/" + fileName); // URL oluşturma
            productPhoto.setProduct(product);
            productPhotoRepository.save(productPhoto);

            return new SuccessDataResult<>(productPhoto, "Fotoğraf başarıyla kaydedildi.");
        } catch (IOException e) {
            return new ErrorDataResult<>("Fotoğraf kaydedilirken bir hata oluştu: " + e.getMessage());
        }
    }
}
