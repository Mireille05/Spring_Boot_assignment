# Spring Boot Microservices Collection

**Author:** Kubwayo Elysee Mireille

This repository contains a collection of Spring Boot applications, each focusing on a specific domain.

## Project List
1.  **[User Profile Management (`userProfileMgt`)](#1-user-profile-management-userprofilemgt)**
2.  **[Library Management (`LibraryMs`)](#2-library-management-libraryms)**
3.  **[Student Management (`Student`)](#3-student-management-student)**
4.  **[E-Commerce Product Catalog (`e-commerce`)](#4-e-commerce-product-catalog-e-commerce)**
5.  **[Restaurant Menu (`restaurantMenu`)](#5-restaurant-menu-restaurantmenu)**
6.  **[Task Manager (`taskManager`)](#6-task-manager-taskmanager)**

## Prerequisites
- Java 17 or higher
- Maven 3.6+ (or use the included Maven Wrapper in each project)

## Running the Applications
Navigate to the specific project directory and run:

### Windows
```cmd
cd [project-folder]
mvnw spring-boot:run
```

### Linux / macOS
```bash
cd [project-folder]
./mvnw spring-boot:run
```

Most applications start on **port 8080**. Check `application.properties` in each project if conflicts arise.

---

## 1. User Profile Management (`userProfileMgt`)
Manage user profiles with CRUD operations, search, and status updates.

### Endpoints
| Method | URL | Description |
| :--- | :--- | :--- |
| `POST` | `/api/users` | Create a new user profile |
| `GET` | `/api/users/{id}` | Get user by ID |
| `GET` | `/api/users` | Get all users |
| `PUT` | `/api/users/{id}` | Update user details |
| `DELETE` | `/api/users/{id}` | Delete user |
| `GET` | `/api/users/search` | Search by username, country, age range |
| `PATCH` | `/api/users/{id}/status` | Activate/deactivate user |

### Sample Request (Create User)
**POST** `/api/users`
```json
{
  "username": "Aurore",
  "email": "Nadine@example.com",
  "fullName": "Aurore Nadine",
  "age": 21,
  "country": "Rwanda",
  "bio": "Software Engineer",
  "active": true
}
```

---

## 2. Library Management (`LibraryMs`)
Manage a library's book collection.

### Endpoints
| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/books` | Get all books |
| `GET` | `/api/books/{id}` | Get book by ID |
| `GET` | `/api/books/search?title={title}` | Search books by title |
| `POST` | `/api/books` | Add a new book |
| `PUT` | `/api/books/{id}` | Update a book |
| `DELETE` | `/api/books/{id}` | Delete a book |

### Sample Request (Add Book)
**POST** `/api/books`
```json
{
  "title": "The Pragmatic Programmer",
  "author": "Andrew Hunt",
  "isbn": "978-0201616224",
  "publicationYear": 1999
}
```

---

## 3. Student Management (`Student`)
Manage student records, including GPA and major.

### Endpoints
| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/students` | Get all students |
| `GET` | `/api/students/{id}` | Get student by ID |
| `GET` | `/api/students/major/{major}` | Filter students by major |
| `GET` | `/api/students/filter?gpa={minGpa}` | Filter students by minimum GPA |
| `POST` | `/api/students` | Register a new student |
| `PUT` | `/api/students/{id}` | Update student info |
| `DELETE` | `/api/students/{id}` | Delete student |

### Sample Request (Register Student)
**POST** `/api/students`
```json
{
  "firstName": "Aurore",
  "lastName": "Nadine",
  "email": "nadine@university.edu",
  "major": "Computer Science",
  "gpa": 3.9
}
```

---

## 4. E-Commerce Product Catalog (`e-commerce`)
Manage product inventory with search, filtering, and stock management.

### Endpoints
| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/products` | Get all products (optional pagination) |
| `GET` | `/api/products/{id}` | Get product details |
| `GET` | `/api/products/category/{cat}` | Filter by category |
| `GET` | `/api/products/brand/{brand}` | Filter by brand |
| `GET` | `/api/products/search?keyword={k}` | Search by keyword |
| `GET` | `/api/products/price-range` | Filter by price range (`min`, `max`) |
| `GET` | `/api/products/in-stock` | Get in-stock products |
| `POST` | `/api/products` | Add new product |
| `PUT` | `/api/products/{id}` | Update product |
| `PATCH` | `/api/products/{id}/stock` | Update stock quantity |
| `DELETE` | `/api/products/{id}` | Delete product |

### Sample Request (Add Product)
**POST** `/api/products`
```json
{
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse",
  "price": 29.99,
  "category": "Electronics",
  "stockQuantity": 50,
  "brand": "Logitech"
}
```

---

## 5. Restaurant Menu (`restaurantMenu`)
Manage a restaurant's menu items.

### Endpoints
| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/menu` | Get full menu |
| `GET` | `/api/menu/{id}` | Get menu item |
| `GET` | `/api/menu/category/{cat}` | Filter by category |
| `GET` | `/api/menu/available` | Filter by availability |
| `GET` | `/api/menu/search?name={name}` | Search by name |
| `POST` | `/api/menu` | Add menu item |
| `PUT` | `/api/menu/{id}/availability` | Toggle availability |
| `DELETE` | `/api/menu/{id}` | Remove item |

### Sample Request (Add Item)
**POST** `/api/menu`
```json
{
  "name": "Caesar Salad",
  "description": "Fresh romaine lettuce with croutons and parmesan",
  "price": 9.99,
  "category": "Appetizer",
  "available": true
}
```

---

## 6. Task Manager (`taskManager`)
Manage to-do lists and tasks.

### Endpoints
| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `GET` | `/api/tasks/status` | Filter by completed status |
| `GET` | `/api/tasks/priority/{prio}` | Filter by priority (LOW/MEDIUM/HIGH) |
| `POST` | `/api/tasks` | Create task |
| `PUT` | `/api/tasks/{id}` | Update task |
| `PATCH` | `/api/tasks/{id}/complete` | Mark as completed |
| `DELETE` | `/api/tasks/{id}` | Delete task |

### Sample Request (Create Task)
**POST** `/api/tasks`
```json
{
  "title": "Review PRs",
  "description": "Check pending pull requests",
  "completed": false,
  "priority": "HIGH",
  "dueDate": "2026-02-12"
}
```
