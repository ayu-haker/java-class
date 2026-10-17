package com.nyxora.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String image;
    private int stock;

    protected Product() {}

    public Product(String name, String category, String description,
                   double price, String image, int stock) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImage() { return image; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
