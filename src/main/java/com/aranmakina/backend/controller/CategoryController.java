package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.category.CategoryCreateDTO;
import com.aranmakina.backend.dto.category.CategoryViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<DataResult<CategoryViewDTO>> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        DataResult<CategoryViewDTO> result = categoryService.createCategory(categoryCreateDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CategoryViewDTO>> getCategoryById(@PathVariable Integer categoryId) {
        DataResult<CategoryViewDTO> result = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<CategoryViewDTO>>> getAllCategories() {
        DataResult<List<CategoryViewDTO>> result = categoryService.getAllCategories();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteCategory(@PathVariable Integer categoryId) {
        Result result = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reorder")
    public ResponseEntity<Result> reorderCategories(@RequestBody List<Integer> orderedCategoryIds) {
        return new ResponseEntity<>(categoryService.reorderCategories(orderedCategoryIds), HttpStatus.OK);
    }
}
