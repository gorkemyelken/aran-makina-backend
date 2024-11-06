package com.aranmakina.backend.repository;

import com.aranmakina.backend.model.FeatureName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureNameRepository extends JpaRepository<FeatureName, Integer> {
    FeatureName findByFeatureNameId(Integer featureNameId);
}
