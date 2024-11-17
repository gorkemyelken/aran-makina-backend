package com.aranmakina.backend.dto.product;

import com.aranmakina.backend.dto.category.CategoryDTO;
import com.aranmakina.backend.dto.productfeature.ProductFeatureViewDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductViewDTO {
    private Integer productId;
    private String name;
    private CategoryDTO category;
    private String description;
    private Double price;
    private List<ProductFeatureViewDTO> features;
    private List<String> photos;
}
