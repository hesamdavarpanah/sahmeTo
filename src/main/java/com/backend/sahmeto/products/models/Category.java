package com.backend.sahmeto.products.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
public class Category {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64, min = 5)
    @Column(name = "name", unique = true)
    private String name;

    @Size(min = 5)
    @Column(name = "description")
    private String description;

    @Size(max = 128, min = 7)
    @Column(name = "category_image_path")
    private String categoryImagePath;

    public Category() {
    }

    public Category(String name, String description, String categoryImagePath) {
        this.name = name;
        this.description = description;
        this.categoryImagePath = categoryImagePath;
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

    public @Size(max = 128, min = 7) String getCategoryImagePath() {
        return categoryImagePath;
    }

    public void setCategoryImagePath(@Size(max = 128, min = 7) String categoryImagePath) {
        this.categoryImagePath = categoryImagePath;
    }
}
