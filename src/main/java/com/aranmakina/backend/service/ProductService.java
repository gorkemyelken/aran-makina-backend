package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.product.ProductCreateDTO;
import com.aranmakina.backend.dto.product.ProductViewDTO;
import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.Product;
import com.aranmakina.backend.model.ProductPhoto;
import com.aranmakina.backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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
}

