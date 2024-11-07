package com.aranmakina.backend.service;

import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.Product;
import com.aranmakina.backend.model.ProductPhoto;
import com.aranmakina.backend.repository.ProductPhotoRepository;
import com.aranmakina.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductPhotoService {

    private final ProductPhotoRepository productPhotoRepository;

    private final ProductRepository productRepository;

    @Value("${product.photo.upload-dir}")
    private String uploadDir;

    public ProductPhotoService(ProductPhotoRepository productPhotoRepository, ProductRepository productRepository) {
        this.productPhotoRepository = productPhotoRepository;
        this.productRepository = productRepository;
    }

    public Result addPhotosToProduct(Integer productId, List<MultipartFile> photos) throws IOException {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            return new ErrorResult("Ürün bulunamadı.");
        }

        if (photos != null && !photos.isEmpty()) {
            for (MultipartFile file : photos) {
                DataResult<ProductPhoto> result = savePhoto(file, product);
                if (!result.isSuccess()) {
                    return new ErrorResult("Fotoğraf eklenirken bir hata oluştu: " + result.getMessage());
                }
                product.getPhotos().add(result.getData());
            }
        }

        productRepository.save(product);
        return new SuccessResult("Fotoğraflar başarıyla eklendi.");
    }

    public DataResult<ProductPhoto> savePhoto(MultipartFile file, Product product) {
        try {
            // Dosya adı için benzersiz bir isim oluşturulur
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Dosya fiziksel olarak belirtilen dizine kaydedilir
            Files.copy(file.getInputStream(), filePath);

            // ProductPhoto nesnesi oluşturulur ve kaydedilir
            ProductPhoto productPhoto = new ProductPhoto();
            productPhoto.setUrl("/img/product/" + fileName);
            productPhoto.setProduct(product);

            // Fotoğrafı veritabanına kaydetme
            productPhotoRepository.save(productPhoto);

            return new SuccessDataResult<>(productPhoto, "Fotoğraf başarıyla kaydedildi.");
        } catch (IOException e) {
            return new ErrorDataResult<>("Fotoğraf kaydedilirken bir hata oluştu: " + e.getMessage());
        }
    }
}
