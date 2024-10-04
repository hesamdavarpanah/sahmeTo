package com.backend.sahmeto.products.contollers;


import com.backend.sahmeto.products.models.Category;
import com.backend.sahmeto.products.payloads.requests.CreateCategory;
import com.backend.sahmeto.products.payloads.responses.MessageResponse;
import com.backend.sahmeto.products.services.CategoryService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/category", produces = "application/json", consumes = "application/json")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RateLimiter(name = "category")
    @GetMapping("/get-all/")
    public List<Category> getAllCategories() {
        try {
            return categoryService.getCategories();
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "category")
    @GetMapping("/get/{id}/")
    public ResponseEntity<?> getCategory(@PathVariable(value = "id") Long categoryId) {
        try {
            Optional<Category> category = categoryService.getCategory(categoryId);
            return ResponseEntity.ok().body(category);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "category")
    @PostMapping("/create/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategory createCategory) {
        try {
            categoryService.createCategory(createCategory);
            return MessageResponse.generateResponse("created", "the category has been created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "category")
    @PutMapping("/update/{id}/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody Category category) {
        try {
            categoryService.updateCategory(categoryId, category);
            return MessageResponse.generateResponse("updated", "the category has been updated successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "category")
    @DeleteMapping("/delete/{id}/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return MessageResponse.generateResponse("deleted", "the category has been deleted successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
