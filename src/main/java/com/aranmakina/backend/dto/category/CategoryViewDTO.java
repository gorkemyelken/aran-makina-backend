package com.aranmakina.backend.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryViewDTO {
    private Integer id;
    private String name;
    private String description;
    private int priority;
}