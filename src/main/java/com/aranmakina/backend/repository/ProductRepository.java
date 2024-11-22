package com.aranmakina.backend.repository;

import com.aranmakina.backend.model.Category;
import com.aranmakina.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductId(Integer productId);

    List<Product> findByCategory(Category category);

    List<Product> findAllByOrderByPriorityDesc();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.features LEFT JOIN FETCH p.photos WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);


}
