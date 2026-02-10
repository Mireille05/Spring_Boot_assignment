package com.example.restaurantMenu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with 8+ sample menu items across all categories
    @PostConstruct
    public void init() {
        // Appetizers
        menuItems.add(new MenuItem(nextId++, "Bruschetta",
                "Grilled bread topped with fresh tomatoes, garlic, basil and olive oil",
                8.99, "Appetizer", true));

        menuItems.add(new MenuItem(nextId++, "Stuffed Mushrooms",
                "Button mushrooms filled with herbed cream cheese and baked golden",
                9.49, "Appetizer", true));

        // Main Courses
        menuItems.add(new MenuItem(nextId++, "Grilled Salmon",
                "Fresh Atlantic salmon fillet grilled with lemon butter sauce, served with seasonal vegetables",
                22.99, "Main Course", true));

        menuItems.add(new MenuItem(nextId++, "Chicken Parmesan",
                "Breaded chicken breast topped with marinara sauce and melted mozzarella, served with spaghetti",
                18.50, "Main Course", true));

        menuItems.add(new MenuItem(nextId++, "Beef Tenderloin Steak",
                "8oz prime beef tenderloin cooked to perfection, served with mashed potatoes and asparagus",
                32.99, "Main Course", false));

        // Desserts
        menuItems.add(new MenuItem(nextId++, "Tiramisu",
                "Classic Italian dessert with layers of espresso-soaked ladyfingers and mascarpone cream",
                10.99, "Dessert", true));

        menuItems.add(new MenuItem(nextId++, "Chocolate Lava Cake",
                "Warm chocolate cake with a molten center, served with vanilla ice cream",
                12.50, "Dessert", true));

        // Beverages
        menuItems.add(new MenuItem(nextId++, "Fresh Lemonade",
                "House-made lemonade with fresh lemons, a hint of mint and a touch of honey",
                4.99, "Beverage", true));

        menuItems.add(new MenuItem(nextId++, "Espresso",
                "Rich and bold double-shot espresso made from premium roasted beans",
                3.50, "Beverage", false));

        menuItems.add(new MenuItem(nextId++, "Mango Smoothie",
                "Tropical mango blended with yogurt and a splash of orange juice",
                6.99, "Beverage", true));
    }

    // GET /api/menu - Get all menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItems);
    }

    // GET /api/menu/{id} - Get specific menu item
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/menu/category/{category} - Get items by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItem> filtered = menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filtered);
    }

    // GET /api/menu/available?available=true - Get only available items
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(
            @RequestParam(defaultValue = "true") boolean available) {
        List<MenuItem> filtered = menuItems.stream()
                .filter(item -> item.isAvailable() == available)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    // GET /api/menu/search?name={name} - Search menu items by name
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItemsByName(@RequestParam String name) {
        List<MenuItem> filtered = menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    // POST /api/menu - Add new menu item
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem.setId(nextId++);
        menuItems.add(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
    }

    // PUT /api/menu/{id}/availability - Toggle item availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(item -> {
                    item.setAvailable(!item.isAvailable());
                    return ResponseEntity.ok(item);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/menu/{id} - Remove menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
