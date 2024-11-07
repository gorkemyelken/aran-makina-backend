package com.aranmakina.backend.repository;

import com.aranmakina.backend.model.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {
}
