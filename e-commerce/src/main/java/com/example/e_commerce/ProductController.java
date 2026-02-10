package com.example.e_commerce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        products.add(new Product(nextId++, "iPhone 15 Pro", "Latest Apple smartphone with A17 Pro chip and titanium design", 1199.99, "Electronics", 25, "Apple"));
        products.add(new Product(nextId++, "Samsung Galaxy S24 Ultra", "Premium Android smartphone with S Pen and AI features", 1099.99, "Electronics", 30, "Samsung"));
        products.add(new Product(nextId++, "Nike Air Max 270", "Lightweight running shoes with Max Air cushioning", 149.99, "Footwear", 50, "Nike"));
        products.add(new Product(nextId++, "Levi's 501 Original Jeans", "Classic straight-fit denim jeans with iconic styling", 69.99, "Clothing", 100, "Levi's"));
        products.add(new Product(nextId++, "Sony WH-1000XM5 Headphones", "Industry-leading noise cancelling wireless headphones", 349.99, "Electronics", 15, "Sony"));
        products.add(new Product(nextId++, "The Great Gatsby", "Classic novel by F. Scott Fitzgerald about the American Dream", 12.99, "Books", 200, "Scribner"));
        products.add(new Product(nextId++, "Adidas Ultraboost 23", "High-performance running shoes with Boost midsole technology", 189.99, "Footwear", 40, "Adidas"));
        products.add(new Product(nextId++, "KitchenAid Stand Mixer", "Professional 5-quart stand mixer for baking enthusiasts", 379.99, "Home & Kitchen", 10, "KitchenAid"));
        products.add(new Product(nextId++, "Wilson Pro Staff Tennis Racket", "Professional-grade tennis racket used by Roger Federer", 249.99, "Sports", 20, "Wilson"));
        products.add(new Product(nextId++, "Instant Pot Duo 7-in-1", "Multi-functional electric pressure cooker for quick meals", 89.99, "Home & Kitchen", 0, "Instant Pot"));
        products.add(new Product(nextId++, "Harry Potter Complete Collection", "All seven Harry Potter books in a premium boxed set", 65.99, "Books", 35, "Bloomsbury"));
        products.add(new Product(nextId++, "North Face Puffer Jacket", "Insulated winter jacket with 700-fill down for extreme warmth", 299.99, "Clothing", 0, "The North Face"));
    }

    // GET /api/products - Get all products (with optional pagination)
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {

        if (page != null && limit != null) {
            int start = page * limit;
            int end = Math.min(start + limit, products.size());

            if (start >= products.size()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(products.subList(start, end));
        }
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{productId} - Get product details
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(p -> ResponseEntity.ok((Object) p))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found with id: " + productId));
    }

    // GET /api/products/category/{category} - Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> result = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET /api/products/brand/{brand} - Get products by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> result = products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET /api/products/search?keyword={keyword} - Search by keyword in name or description
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<Product> result = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword)
                        || p.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET /api/products/price-range?min={min}&max={max} - Get products within price range
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Product> result = products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET /api/products/in-stock - Get products with stockQuantity > 0
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> result = products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // POST /api/products - Add new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setProductId(nextId++);
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // PUT /api/products/{productId} - Update product details
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                updatedProduct.setProductId(productId);
                products.set(i, updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found with id: " + productId);
    }

    // PATCH /api/products/{productId}/stock?quantity={quantity} - Update stock quantity
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long productId,
                                         @RequestParam int quantity) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setStockQuantity(quantity);
                return ResponseEntity.ok(product);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found with id: " + productId);
    }

    // DELETE /api/products/{productId} - Delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found with id: " + productId);
    }
}
