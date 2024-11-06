package com.aranmakina.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "product")
public class ProductFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productFeatureId;

    @ManyToOne
    @JoinColumn(name = "feature_name_id")
    private FeatureName featureName;

    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
