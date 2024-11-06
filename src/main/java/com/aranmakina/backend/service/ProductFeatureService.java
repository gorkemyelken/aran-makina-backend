package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.productfeature.ProductFeatureCreateDTO;
import com.aranmakina.backend.dto.productfeature.ProductFeatureViewDTO;
import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.FeatureName;
import com.aranmakina.backend.model.Product;
import com.aranmakina.backend.model.ProductFeature;
import com.aranmakina.backend.repository.FeatureNameRepository;
import com.aranmakina.backend.repository.ProductFeatureRepository;
import com.aranmakina.backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductFeatureService {

    private final ProductFeatureRepository productFeatureRepository;

    private final ProductRepository productRepository;

    private final FeatureNameRepository featureNameRepository;
    private final ModelMapper modelMapper;

    public ProductFeatureService(ProductFeatureRepository productFeatureRepository, ProductRepository productRepository, FeatureNameRepository featureNameRepository, ModelMapper modelMapper) {
        this.productFeatureRepository = productFeatureRepository;
        this.productRepository = productRepository;
        this.featureNameRepository = featureNameRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<List<ProductFeatureViewDTO>> getAll() {
        List<ProductFeature> productFeatures = productFeatureRepository.findAll();
        if (productFeatures.isEmpty()) {
            return new ErrorDataResult<>("Ürün özellikleri bulunamadı.");
        }
        List<ProductFeatureViewDTO> productFeatureViewDTOs = productFeatures.stream()
                .map(productFeature -> modelMapper.map(productFeature, ProductFeatureViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(productFeatureViewDTOs, "Ürün özellikleri listelendi.");
    }

    public DataResult<ProductFeatureViewDTO> add(ProductFeatureCreateDTO productFeatureCreateDTO) {
        Product product = productRepository.findByProductId(productFeatureCreateDTO.getProductId());

        if(product == null)
        {
            return new ErrorDataResult<>("Ürün bulunamadı.");
        }

        FeatureName featureName = featureNameRepository.findByFeatureNameId(productFeatureCreateDTO.getFeatureNameId());

        if(featureName == null){
            return new ErrorDataResult<>("Özellik ismi bulunamadı.");
        }

        ProductFeature productFeature = new ProductFeature();
        productFeature.setFeatureName(featureName);
        productFeature.setValue(productFeatureCreateDTO.getValue());
        productFeature.setProduct(product);
        productFeatureRepository.save(productFeature);

        ProductFeatureViewDTO productFeatureViewDTO = modelMapper.map(productFeature, ProductFeatureViewDTO.class);
        return new SuccessDataResult<>(productFeatureViewDTO, "Ürün özelliği eklendi.");
    }

    public Result delete(Integer productFeatureId) {
        this.productFeatureRepository.deleteById(productFeatureId);
        return new SuccessResult("Ürün özelliği silindi.");
    }
}
