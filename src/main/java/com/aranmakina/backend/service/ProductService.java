package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.product.ProductCreateDTO;
import com.aranmakina.backend.dto.product.ProductUpdateDTO;
import com.aranmakina.backend.dto.product.ProductViewDTO;
import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.Category;
import com.aranmakina.backend.model.Product;
import com.aranmakina.backend.model.ProductPhoto;
import com.aranmakina.backend.repository.CategoryRepository;
import com.aranmakina.backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public DataResult<List<ProductViewDTO>> getAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return new ErrorDataResult<>("Ürünler bulunamadı.");
        }
        List<ProductViewDTO> productViewDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(productViewDTOS, "Ürünler listelendi.");
    }

    public DataResult<ProductViewDTO> add(ProductCreateDTO productCreateDTO) {
        Product product = modelMapper.map(productCreateDTO, Product.class);
        productRepository.save(product);
        ProductViewDTO productViewDTO = modelMapper.map(product, ProductViewDTO.class);
        return new SuccessDataResult<>(productViewDTO, "Ürün eklendi.");
    }

    public Result delete(Integer productId) {
        productRepository.deleteById(productId);
        return new SuccessResult("Ürün silindi.");
    }

    public DataResult<ProductViewDTO> findById(Integer productId) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            return new ErrorDataResult<>("Ürün bulunamadı.");
        }
        ProductViewDTO productViewDTO = modelMapper.map(product, ProductViewDTO.class);
        return new SuccessDataResult<>(productViewDTO, "Ürün bulundu.");
    }

    public DataResult<ProductViewDTO> update(Integer productId, ProductUpdateDTO productUpdateDTO) {
        Product existingProduct = productRepository.findByProductId(productId);
        if (existingProduct == null) {
            return new ErrorDataResult<>("Ürün bulunamadı.");
        }

        existingProduct.setName(productUpdateDTO.getName());
        existingProduct.setCategory(productUpdateDTO.getCategory());
        existingProduct.setDescription(productUpdateDTO.getDescription());
        existingProduct.setPrice(productUpdateDTO.getPrice());

        productRepository.save(existingProduct);
        ProductViewDTO updatedProductViewDTO = modelMapper.map(existingProduct, ProductViewDTO.class);

        return new SuccessDataResult<>(updatedProductViewDTO, "Ürün güncellendi.");
    }

    public DataResult<List<ProductViewDTO>> getProductsByCategory(Integer categoryId) {
        // Verilen ID'ye göre Category'yi bulun
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));

        // Bu Category'ye ait Product'ları bulun
        List<Product> products = productRepository.findByCategory(category);

        if (products.isEmpty()) {
            return new ErrorDataResult<>("Bu kategoride ürün yok.");
        }

        // Product'ları DTO'ya map'leyin
        List<ProductViewDTO> productViewDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductViewDTO.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(productViewDTOS, "Ürünler kategoriye göre listelendi.");
    }


    public DataResult<List<ProductViewDTO>> searchProducts(String keyword) {
        List<Product> products = productRepository.searchByKeyword(keyword);
        List<ProductViewDTO> productViewDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(productViewDTOS, "Ürünler listelendi.");
    }


    public boolean addProductPhoto(Integer productId, String photoUrl) {
        Product productOpt = productRepository.findByProductId(productId);

        ProductPhoto productPhoto = new ProductPhoto();
        productPhoto.setUrl(photoUrl);

        productOpt.getPhotos().add(productPhoto);  // Fotoğraf ekleme işlemi
        productRepository.save(productOpt); // Güncellenmiş ürünü kaydetme
        return true;
    }

}

