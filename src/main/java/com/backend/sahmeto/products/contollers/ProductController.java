package com.backend.sahmeto.products.contollers;

import com.backend.sahmeto.authentication.models.User;
import com.backend.sahmeto.products.models.Product;
import com.backend.sahmeto.products.payloads.requests.ConfirmProduct;
import com.backend.sahmeto.products.payloads.requests.CreateProduct;
import com.backend.sahmeto.products.payloads.requests.UpdateProduct;
import com.backend.sahmeto.products.payloads.responses.MessageResponse;
import com.backend.sahmeto.products.respositories.CategoryRepository;
import com.backend.sahmeto.products.services.ProductService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/product", produces = "application/json", consumes = "application/json")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @RateLimiter(name = "product")
    @GetMapping("/get-all/")
    public List<Product> getAllProducts() {
        try {
            return productService.getAllProducts();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "product")
    @GetMapping("/get/{id}/")
    public Optional<Product> getProduct(@PathVariable(value = "id") Long productId) {
        try {
            return productService.getProduct(productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "product")
    @PostMapping("/create/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal User user, CreateProduct createProduct) {
        try {
            productService.createProduct(createProduct.getCategoryId(), user.getUsername(), createProduct);
            return MessageResponse.generateResponse("created", "the product has been created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "product")
    @PutMapping("/update/{id}/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long productId, UpdateProduct updateProduct) {
        try {
            productService.updateProduct(productId, updateProduct.getCategoryId(), updateProduct);
            return MessageResponse.generateResponse("updated", "the product has been updated successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "product")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId) {
        try {
            productService.deleteProduct(productId);
            return MessageResponse.generateResponse("deleted", "the product has been deleted successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "product")
    @PatchMapping("/confirm/{id}/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> confirmProduct(@PathVariable(value = "id") Long productId, ConfirmProduct confirmProduct) {
        try {
            productService.updateConfirmProduct(productId, confirmProduct);
            return MessageResponse.generateResponse("confirmed", "the product has been confirmed successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
