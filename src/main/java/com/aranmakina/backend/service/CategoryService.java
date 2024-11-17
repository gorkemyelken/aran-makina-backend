package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.category.CategoryCreateDTO;
import com.aranmakina.backend.dto.category.CategoryDTO;
import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.Category;
import com.aranmakina.backend.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create operation
    public DataResult<CategoryDTO> createCategory(CategoryCreateDTO categoryCreateDTO) {
        Category category = modelMapper.map(categoryCreateDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO categoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return new SuccessDataResult<>(categoryDTO, "Kategori başarıyla oluşturuldu.");
    }

    // Read operation
    public DataResult<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ErrorDataResult<>("Kategori bulunamadı.");
        }
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(categoryDTOs, "Kategoriler başarıyla listelendi.");
    }

    public DataResult<CategoryDTO> getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return new SuccessDataResult<>(categoryDTO, "Kategori başarıyla bulundu.");
    }

    // Delete operation
    public Result deleteCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return new ErrorResult("Kategori bulunamadı.");
        }
        categoryRepository.deleteById(categoryId);
        return new SuccessResult("Kategori başarıyla silindi.");
    }
}

