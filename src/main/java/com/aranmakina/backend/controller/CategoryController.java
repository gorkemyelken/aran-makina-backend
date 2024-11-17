package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.category.CategoryCreateDTO;
import com.aranmakina.backend.dto.category.CategoryDTO;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<DataResult<CategoryDTO>> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        DataResult<CategoryDTO> result = categoryService.createCategory(categoryCreateDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CategoryDTO>> getCategoryById(@PathVariable Integer id) {
        DataResult<CategoryDTO> result = categoryService.getCategoryById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<CategoryDTO>>> getAllCategories() {
        DataResult<List<CategoryDTO>> result = categoryService.getAllCategories();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteCategory(@PathVariable Integer id) {
        Result result = categoryService.deleteCategory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
