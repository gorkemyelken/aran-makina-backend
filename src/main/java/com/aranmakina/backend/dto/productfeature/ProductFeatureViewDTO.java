package com.aranmakina.backend.dto.productfeature;

import com.aranmakina.backend.dto.featurename.FeatureNameViewDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFeatureViewDTO {
    private Integer productFeatureId;
    private FeatureNameViewDTO featureName;
    private String value;
    private Integer productId;
}
