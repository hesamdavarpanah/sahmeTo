package com.backend.sahmeto.products.services;

import com.backend.sahmeto.products.models.Category;
import com.backend.sahmeto.products.payloads.requests.CreateCategory;
import com.backend.sahmeto.products.respositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public void createCategory(CreateCategory createCategory) {
        Category category = new Category(createCategory.getName(),
                createCategory.getDescription(),
                createCategory.getCategoryImagePath());
        categoryRepository.save(category);
    }

    public void updateCategory(Long categoryId, Category categoryUpdate) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryUpdate.getName());
        category.setDescription(categoryUpdate.getDescription());
        category.setCategoryImagePath(categoryUpdate.getCategoryImagePath());

        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

}
