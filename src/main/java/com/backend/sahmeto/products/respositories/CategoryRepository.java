package com.backend.sahmeto.products.respositories;

import com.backend.sahmeto.products.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
