package com.aranmakina.backend.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDTO {
    private Integer categoryId;
    private String name;
    private String description;
    private int priority;
}
