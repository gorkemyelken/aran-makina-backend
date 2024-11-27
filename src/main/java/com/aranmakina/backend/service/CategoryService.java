package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.category.CategoryCreateDTO;
import com.aranmakina.backend.dto.category.CategoryViewDTO;
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
    public DataResult<CategoryViewDTO> createCategory(CategoryCreateDTO categoryCreateDTO) {
        Category category = modelMapper.map(categoryCreateDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryViewDTO categoryDTO = modelMapper.map(savedCategory, CategoryViewDTO.class);
        return new SuccessDataResult<>(categoryDTO, "Kategori başarıyla oluşturuldu.");
    }

    // Read operation
    public DataResult<List<CategoryViewDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByOrderByPriorityDesc();
        if (categories.isEmpty()) {
            return new ErrorDataResult<>("Kategori bulunamadı.");
        }
        List<CategoryViewDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(categoryDTOs, "Kategoriler başarıyla listelendi.");
    }

    public DataResult<CategoryViewDTO> getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId);
        CategoryViewDTO categoryDTO = modelMapper.map(category, CategoryViewDTO.class);
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

    public Result reorderCategories(List<Integer> orderedCategoryIds) {
        int priority = orderedCategoryIds.size();
        for (Integer categoryId : orderedCategoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + categoryId));
            category.setPriority(priority--);
        }
        categoryRepository.saveAll(categoryRepository.findAllById(orderedCategoryIds));
        return new SuccessResult("Kategori sıralaması güncellendi.");
    }

    public boolean addCategoryPhoto(Integer categoryId, String photoUrl) {
        Category categoryOpt = categoryRepository.findByCategoryId(categoryId);
        categoryOpt.setCategoryPhotoUrl(photoUrl);
        categoryRepository.save(categoryOpt); // Güncellenmiş ürünü kaydetme
        return true;
    }
}

