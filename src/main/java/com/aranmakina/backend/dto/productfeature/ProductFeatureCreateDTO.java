package com.aranmakina.backend.dto.productfeature;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFeatureCreateDTO {
    private Integer productId;

    private Integer featureNameId;

    private String value;
}
