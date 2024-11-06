package com.aranmakina.backend.repository;

import com.aranmakina.backend.model.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Integer> {
}
