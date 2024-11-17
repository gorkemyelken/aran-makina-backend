package com.aranmakina.backend.dto.product;

import com.aranmakina.backend.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDTO {
    private String name;
    private Integer category;
    private String description;
    private Double price;
}
