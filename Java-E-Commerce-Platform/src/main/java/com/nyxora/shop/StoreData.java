package com.nyxora.shop;

import com.nyxora.shop.model.Product;
import com.nyxora.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreData {
    @Bean
    CommandLineRunner seedProducts(ProductRepository products) {
        return args -> {
            if (products.count() == 0) {
                products.save(new Product("Studio Headphones", "Audio",
                    "Immersive sound for music, study and everyday listening.",
                    2499, "https://placehold.co/600x400/171a2b/ffffff?text=Headphones", 15));
                products.save(new Product("Urban Backpack", "Accessories",
                    "A clean everyday backpack with room for your essentials.",
                    1799, "https://placehold.co/600x400/24352c/ffffff?text=Backpack", 20));
                products.save(new Product("Mechanical Keyboard", "Tech",
                    "A satisfying typing experience for work and gaming.",
                    3299, "https://placehold.co/600x400/30243d/ffffff?text=Keyboard", 10));
                products.save(new Product("Smart Desk Lamp", "Home",
                    "Minimal lighting for focused work and relaxed evenings.",
                    1299, "https://placehold.co/600x400/403424/ffffff?text=Desk+Lamp", 12));
                products.save(new Product("Wireless Mouse", "Tech",
                    "Comfortable wireless control for your daily workflow.",
                    899, "https://placehold.co/600x400/203744/ffffff?text=Mouse", 25));
                products.save(new Product("Insulated Bottle", "Lifestyle",
                    "A reusable bottle designed for busy days on the go.",
                    699, "https://placehold.co/600x400/3d3030/ffffff?text=Bottle", 30));
            }
        };
    }
}
