package com.aranmakina.backend.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDTO {
    private String name;
    private String category;
    private String description;
    private Double price;
}
