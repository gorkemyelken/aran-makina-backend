package com.aranmakina.backend.dto.product;

import com.aranmakina.backend.model.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDTO {
    private String name;
    private CategoryType category;
    private String description;
    private Double price;
}
