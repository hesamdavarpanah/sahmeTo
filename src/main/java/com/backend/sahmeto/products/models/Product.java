package com.backend.sahmeto.products.models;

import com.backend.sahmeto.authentication.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64, min = 5)
    @Column(name = "name")
    private String name;

    @Size(min = 5)
    @Column(name = "description")
    private String description;

    @Size(max = 128, min = 7)
    @Column(name = "product_image_path")
    private String productImagePath;

    @NotBlank
    @Column(name = "base_price")
    private int basePrice;

    @NotBlank
    @Column(name = "low_bet")
    private int lowBet;

    @NotBlank
    @Column(name = "lottery_date")
    private LocalDateTime lotteryDate;

    @NotBlank
    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @NotBlank
    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @NotBlank
    @OneToOne
    @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Category category;

    @NotBlank
    @ManyToOne
    @JoinTable(name = "user_products", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    public Product() {
    }

    public Product(String name, String description, String productImagePath, int basePrice, int lowBet, LocalDateTime lotteryDate, LocalDateTime publishDate, boolean isConfirmed, Category category, User user) {
        this.name = name;
        this.description = description;
        this.productImagePath = productImagePath;
        this.basePrice = basePrice;
        this.lowBet = lowBet;
        this.lotteryDate = lotteryDate;
        this.publishDate = publishDate;
        this.isConfirmed = isConfirmed;
        this.category = category;
        this.user = user;
    }

    public @NotBlank @Size(max = 64, min = 5) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(max = 64, min = 5) String name) {
        this.name = name;
    }

    public @Size(min = 5) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 5) String description) {
        this.description = description;
    }

    public @Size(max = 128, min = 7) String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(@Size(max = 128, min = 7) String productImagePath) {
        this.productImagePath = productImagePath;
    }

    @NotBlank
    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(@NotBlank int basePrice) {
        this.basePrice = basePrice;
    }

    @NotBlank
    public int getLowBet() {
        return lowBet;
    }

    public void setLowBet(@NotBlank int lowBet) {
        this.lowBet = lowBet;
    }

    public @NotBlank LocalDateTime getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(@NotBlank LocalDateTime lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

    public @NotBlank LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(@NotBlank LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public @NotBlank Category getCategory() {
        return category;
    }

    public void setCategory(@NotBlank Category category) {
        this.category = category;
    }

    public @NotBlank User getUser() {
        return user;
    }

    public void setUser(@NotBlank User user) {
        this.user = user;
    }
}
