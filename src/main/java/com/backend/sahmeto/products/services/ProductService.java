package com.backend.sahmeto.products.services;

import com.backend.sahmeto.authentication.models.User;
import com.backend.sahmeto.authentication.repositories.UserRepository;
import com.backend.sahmeto.products.models.Category;
import com.backend.sahmeto.products.models.Product;
import com.backend.sahmeto.products.payloads.requests.ConfirmProduct;
import com.backend.sahmeto.products.payloads.requests.CreateProduct;
import com.backend.sahmeto.products.payloads.requests.UpdateProduct;
import com.backend.sahmeto.products.respositories.CategoryRepository;
import com.backend.sahmeto.products.respositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public void createProduct(Long categoryId, String username, CreateProduct createProduct) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));

        Product product = new Product(createProduct.getName(), createProduct.getDescription(), createProduct.getProductImagePath(), createProduct.getBasePrice(), createProduct.getLowBet(), createProduct.getLotteryDate(), LocalDateTime.now(), false, category, user);
        productRepository.save(product);
    }

    public void updateProduct(Long productId, Long categoryId, UpdateProduct updateProduct) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setProductImagePath(updateProduct.getProductImagePath());
        product.setBasePrice(updateProduct.getBasePrice());
        product.setLowBet(updateProduct.getLowBet());
        product.setLotteryDate(updateProduct.getLotteryDate());
        product.setCategory(category);

        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    public void updateConfirmProduct(Long productId, ConfirmProduct confirmProduct) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setConfirmed(confirmProduct.getIsConfirmed());

        productRepository.save(product);
    }
}
