package com.aranmakina.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class FeatureName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer featureNameId;

    private String name;
}
