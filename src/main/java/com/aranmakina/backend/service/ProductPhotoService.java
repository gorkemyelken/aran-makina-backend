package com.aranmakina.backend.service;

import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.Product;
import com.aranmakina.backend.model.ProductPhoto;
import com.aranmakina.backend.repository.ProductPhotoRepository;
import com.aranmakina.backend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductPhotoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductPhotoService.class);
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

        LOGGER.info("[addPhotosToProduct] Ürün bulundu.");

        List<ProductPhoto> savedPhotos = new ArrayList<>();

        if (photos != null && !photos.isEmpty()) {
            LOGGER.info("[addPhotosToProduct] Fotolar null degil.");
            for (MultipartFile file : photos) {
                // Dosya adı normalize edilir ve benzersiz hale getirilir
                String originalFileName = file.getOriginalFilename();
                LOGGER.info("[addPhotosToProduct] originalFileName {}.", originalFileName);
                String normalizedFileName = java.text.Normalizer
                        .normalize(originalFileName, java.text.Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .replaceAll("\\s", "_");
                LOGGER.info("[addPhotosToProduct] normalizedFileName {}.", normalizedFileName);
                String fileName = System.currentTimeMillis() + "_" + normalizedFileName;
                LOGGER.info("[addPhotosToProduct] fileName {}.", fileName);
                Path filePath = Paths.get(uploadDir, fileName);
                LOGGER.info("[addPhotosToProduct] filePath {}.", filePath);
                try {
                    // Dosya /tmp dizinine kaydedilir
                    Files.copy(file.getInputStream(), filePath);
                    LOGGER.info("[addPhotosToProduct] Dosya /tmp dizinine kaydedilir.");
                    // ProductPhoto nesnesi oluşturulur
                    ProductPhoto productPhoto = new ProductPhoto();
                    productPhoto.setUrl("/img/product/" + fileName);
                    productPhoto.setProduct(product);

                    // Fotoğraf kaydedilir ve listeye eklenir
                    productPhotoRepository.save(productPhoto);
                    savedPhotos.add(productPhoto);
                } catch (IOException e) {
                    return new ErrorResult("Fotoğraf kaydedilirken bir hata oluştu: " + e.getMessage());
                }
            }
        }

        productRepository.save(product);
        return new SuccessResult("Fotoğraflar başarıyla eklendi.");
    }


    public DataResult<List<ProductPhoto>> savePhotos(List<MultipartFile> files, Product product) {
        List<ProductPhoto> savedPhotos = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                String normalizedFileName = java.text.Normalizer
                        .normalize(originalFileName, java.text.Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .replaceAll("\\s", "_");

                String fileName = System.currentTimeMillis() + "_" + normalizedFileName;
                Path filePath = Paths.get(uploadDir, fileName);

                // Dosyayı belirtilen dizine kaydet
                Files.copy(file.getInputStream(), filePath);

                // ProductPhoto nesnesini oluştur
                ProductPhoto productPhoto = new ProductPhoto();
                productPhoto.setUrl("/img/product/" + fileName);
                productPhoto.setProduct(product);

                // Fotoğrafı kaydet ve listeye ekle
                productPhotoRepository.save(productPhoto);
                savedPhotos.add(productPhoto);
            }

            return new SuccessDataResult<>(savedPhotos, "Fotoğraflar başarıyla kaydedildi.");
        } catch (IOException e) {
            return new ErrorDataResult<>("Fotoğraflar kaydedilirken bir hata oluştu: " + e.getMessage());
        }
    }


}
