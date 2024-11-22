package com.aranmakina.backend.dto.product;

import com.aranmakina.backend.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDTO {
    private String name;
    private Category category;
    private String description;
    private Double price;
    private int priority;
}
