package com.backend.sahmeto.products.respositories;

import com.backend.sahmeto.products.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
