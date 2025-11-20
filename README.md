# Inventory Management System

A comprehensive monolithic backend application built with Spring Boot for managing inventory in small businesses. Features product management, category organization, user authentication, and advanced querying capabilities.

---

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Security](#security)
- [AOP Features](#aop-features)
- [Custom Queries](#custom-queries)
- [Future Enhancements](#future-enhancements)

---

## ✨ Features

### Product Management
- ✅ Create, Read, Update, Delete (CRUD) operations
- ✅ Search products by name (partial match, case-insensitive)
- ✅ Filter products by price range
- ✅ Find low stock products (reorder alerts)
- ✅ Find out-of-stock products
- ✅ Premium products filter (per-unit price)
- ✅ Calculate total inventory value
- ✅ Count products in stock

### Category Management
- ✅ CRUD operations for categories
- ✅ Assign products to categories
- ✅ View products by category
- ✅ Auto-create categories when creating products

### User Authentication
- ✅ User signup with email hashing (MD5)
- ✅ Password encryption (BCrypt)
- ✅ Role-based user system (ADMIN, STAFF, USER)
- ✅ Track user creation and last login

### Advanced Features
- ✅ Service layer architecture (separation of concerns)
- ✅ Custom repository queries (JPQL)
- ✅ Aspect-Oriented Programming (AOP) for logging
- ✅ Transaction management
- ✅ Input validation
- ✅ Error handling with custom exceptions

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Programming Language |
| Spring Boot | 3.2.x | Backend Framework |
| Spring Data JPA | 3.2.x | Database ORM |
| PostgreSQL | 18.x | Relational Database |
| Hibernate | 6.x | JPA Implementation |
| Spring Security | 6.x | Authentication & Authorization |
| Spring AOP | 6.x | Cross-cutting Concerns |
| Lombok | Latest | Boilerplate Code Reduction |
| BCrypt | - | Password Encryption |
| Maven | 3.9.x | Build Tool |

---

## 🏗️ Architecture

### Layered Architecture
```
┌─────────────────────────────────────────┐
│         Controller Layer                 │  ← HTTP Requests/Responses
├─────────────────────────────────────────┤
│         Service Layer                    │  ← Business Logic & Validation
├─────────────────────────────────────────┤
│         Repository Layer                 │  ← Database Operations
├─────────────────────────────────────────┤
│         Database (PostgreSQL)            │  ← Data Storage
└─────────────────────────────────────────┘
           ↑
    AOP Aspects (Logging, Security, Transactions)
```

### Design Patterns Used
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic separation
- **DTO Pattern**: Data transfer objects for API
- **Aspect-Oriented Programming**: Cross-cutting concerns

---

## 📁 Project Structure
```
inventory/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bussiness/inventory/
│   │   │       ├── annotation/          # Custom annotations
│   │   │       │   ├── Encrypt.java
│   │   │       │   └── Decrypt.java
│   │   │       ├── aspect/              # AOP Aspects
│   │   │       │   ├── LoggingAspect.java
│   │   │       │   ├── SecurityAspect.java
│   │   │       │   └── TransactionAspect.java
│   │   │       ├── config/              # Configuration
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controller/          # REST Controllers
│   │   │       │   ├── ProductController.java
│   │   │       │   ├── CategoryController.java
│   │   │       │   └── AuthController.java
│   │   │       ├── dto/                 # Data Transfer Objects
│   │   │       │   ├── SignupRequest.java
│   │   │       │   └── SignupResponse.java
│   │   │       ├── model/               # JPA Entities
│   │   │       │   ├── Product.java
│   │   │       │   ├── Category.java
│   │   │       │   └── User.java
│   │   │       ├── repository/          # Data Access Layer
│   │   │       │   ├── ProductRepository.java
│   │   │       │   ├── CategoryRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── service/             # Business Logic Layer
│   │   │       │   ├── ProductService.java
│   │   │       │   ├── CategoryService.java
│   │   │       │   └── UserService.java
│   │   │       ├── util/                # Utility Classes
│   │   │       │   └── EncryptionUtil.java
│   │   │       └── InventoryApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL 18.x
- Maven 3.9.x
- IDE (IntelliJ IDEA, VS Code, or Eclipse)

### Installation

1. **Clone the repository**
```bash
   git clone <repository-url>
   cd inventory
```

2. **Create PostgreSQL database**
```sql
   CREATE DATABASE inventory_db;
```

3. **Configure database connection**
   
   Edit `src/main/resources/application.properties`:
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.properties.hibernate.format_sql=true
```

4. **Build the project**
```bash
   ./mvnw clean install
```

5. **Run the application**
```bash
   ./mvnw spring-boot:run
```

6. **Access the application**
   - Base URL: `http://localhost:8080`
   - API Endpoints: `http://localhost:8080/api/*`

---

## 📡 API Endpoints

### Authentication Endpoints

#### Signup
```http
POST /api/auth/signup
Content-Type: application/json

{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "username": "johndoe",
    "password": "password123",
    "role": "ADMIN"
}
```

**Response:**
```json
{
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "username": "johndoe",
    "role": "ADMIN",
    "message": "User registered successfully"
}
```

---

### Product Endpoints

#### Get All Products
```http
GET /api/products
```

#### Get Product by ID
```http
GET /api/products/{id}
```

#### Create Product (Simple)
```http
POST /api/products
Content-Type: application/json

{
    "name": "Laptop",
    "price": 50000.0,
    "quantity": 10
}
```

#### Create Product with Category ID
```http
POST /api/products?categoryId=1
Content-Type: application/json

{
    "name": "Laptop",
    "price": 50000.0,
    "quantity": 10
}
```

#### Create Product with Category Name
```http
POST /api/products?categoryName=Electronics
Content-Type: application/json

{
    "name": "Laptop",
    "price": 50000.0,
    "quantity": 10
}
```

#### Update Product
```http
PUT /api/products/{id}
Content-Type: application/json

{
    "name": "Updated Laptop",
    "price": 55000.0,
    "quantity": 8,
    "category": {
        "id": 1
    }
}
```

#### Delete Product
```http
DELETE /api/products/{id}
```

#### Assign Category to Product
```http
PATCH /api/products/{productId}/category/{categoryId}
```

#### Remove Category from Product
```http
DELETE /api/products/{productId}/category
```

#### Search Products by Name
```http
GET /api/products/search?name=laptop
```

#### Get Products by Price Range
```http
GET /api/products/price-range?minPrice=1000&maxPrice=50000
```

#### Get Low Stock Products
```http
GET /api/products/low-stock?threshold=10
```

#### Get Out of Stock Products
```http
GET /api/products/out-of-stock
```

#### Get Premium Products
```http
GET /api/products/premium?price=50000
```

#### Get Total Inventory Value
```http
GET /api/products/inventory-value
```

#### Get Products in Stock Count
```http
GET /api/products/count-in-stock
```

#### Get Products by Category
```http
GET /api/products/category/{categoryId}
```

---

### Category Endpoints

#### Get All Categories
```http
GET /api/categories
```

#### Get Category by ID
```http
GET /api/categories/{id}
```

#### Create Category
```http
POST /api/categories
Content-Type: application/json

{
    "name": "Electronics",
    "description": "Electronic items and gadgets"
}
```

#### Update Category
```http
PUT /api/categories/{id}
Content-Type: application/json

{
    "name": "Electronics",
    "description": "Updated description"
}
```

#### Delete Category
```http
DELETE /api/categories/{id}
```

---

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,     -- Stored as MD5 hash
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,          -- Encrypted with BCrypt
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);
```

### Categories Table
```sql
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500)
);
```

### Products Table
```sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INTEGER NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

### Entity Relationships
```
Categories (1) ←→ (*) Products
                ↓
        One-to-Many Relationship
```

---

## 🔒 Security

### Password Encryption
- **Algorithm**: BCrypt
- **Salt**: Automatically generated per password
- **Rounds**: 10 (default)

**Example:**
```
Plain password: "password123"
Encrypted:      "$2a$10$N9qo8uLOickgx2ZMRZoMye..."
```

### Email Hashing
- **Algorithm**: MD5
- **Purpose**: Privacy (email stored as hash)

**Example:**
```
Plain email: "john@example.com"
MD5 hash:    "4c2a8fe7eaf24721cc7a9f0175115bd4"
```

### Current Security Configuration
```java
// Currently: All endpoints are public (for development)
.authorizeHttpRequests(auth -> auth
    .anyRequest().permitAll()
)
```

**⚠️ Note**: This should be changed for production to require authentication.

---

## 🎯 AOP Features

### 1. Logging Aspect
Automatically logs all method calls with:
- Method name
- Arguments
- Execution time
- Success/failure status

**Console output:**
```
🔵 [CONTROLLER] Calling: createProduct with arguments: [Product(...)]
⚙️  [SERVICE] Executing: createProduct with arguments: [Product(...)]
⏱️  [PERFORMANCE] createProduct executed in 123 ms
✅ [SUCCESS] createProduct completed successfully
```

### 2. Security Aspect
Monitors security-sensitive operations:
- User signups
- Delete operations
- Data modifications

**Console output:**
```
🔒 [SECURITY] User signup attempt - Method: signup
🗑️  [SECURITY] Delete operation - Method: deleteProduct
```

### 3. Transaction Aspect
Tracks database transactions:
- Transaction start
- Successful commits
- Rollbacks on failure

**Console output:**
```
💾 [TRANSACTION] Starting transaction for: signup
✅ [TRANSACTION] Transaction committed successfully for: signup
```

---

## 🔍 Custom Queries

### JPQL Queries

#### Search by Name
```java
@Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
List<Product> searchByName(@Param("name") String name);
```

#### Price Range
```java
@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
```

#### Low Stock
```java
@Query("SELECT p FROM Product p WHERE p.quantity < :threshold")
List<Product> findLowStockProducts(@Param("threshold") Integer threshold);
```

#### Total Inventory Value
```java
@Query("SELECT SUM(p.price * p.quantity) FROM Product p")
Double getTotalInventoryValue();
```

#### Count In Stock
```java
@Query("SELECT COUNT(p) FROM Product p WHERE p.quantity > 0")
Long countProductsInStock();
```

#### Premium Products (Per-Unit Price)
```java
@Query("SELECT p FROM Product p WHERE p.quantity > 0 AND (p.price / p.quantity) > :pricePerUnit")
List<Product> findPremiumProducts(@Param("pricePerUnit") Double pricePerUnit);
```

---

## 📊 Business Logic Examples

### Validation
All inputs are validated in the service layer:
- Product name: Required, min 2 characters
- Price: Required, must be positive
- Quantity: Required, cannot be negative
- Email: Valid format, unique
- Password: Minimum 6 characters

### Automatic Features
- **Low stock alerts**: Console warning when products are low
- **Category auto-creation**: Creates category if it doesn't exist
- **Email hashing**: Automatically hashes emails before storage
- **Password encryption**: Automatically encrypts passwords with BCrypt
- **Timestamp tracking**: Auto-sets creation time and last login

---

## 🧪 Testing

### Using Postman/cURL

#### Example: Create Category and Product
```bash
# 1. Create category
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Electronics",
    "description": "Electronic items"
  }'

# 2. Create product with category
curl -X POST http://localhost:8080/api/products?categoryId=1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "price": 50000.0,
    "quantity": 10
  }'

# 3. Search products
curl -X GET "http://localhost:8080/api/products/search?name=laptop"
```

---

## 🐛 Error Handling

### Common Errors and Solutions

#### 1. Division by Zero Error
**Error**: `ERROR: division by zero` when querying premium products

**Cause**: Products with quantity = 0

**Solution**: Query excludes zero-quantity products
```java
@Query("SELECT p FROM Product p WHERE p.quantity > 0 AND (p.price / p.quantity) > :pricePerUnit")
```

#### 2. Category Already Exists
**Error**: `Category with name 'Electronics' already exists`

**Cause**: Duplicate category name

**Solution**: Check if category exists before creating

#### 3. Product Not Found
**Error**: `Product not found with id: 5`

**Cause**: Invalid product ID

**Solution**: Verify product exists before operations

---

## 🔮 Future Enhancements

### Phase 1: Authentication Complete
- [ ] Add login endpoint with JWT tokens
- [ ] Implement refresh tokens
- [ ] Add email verification
- [ ] Forgot password functionality

### Phase 2: Advanced Features
- [ ] Add Supplier entity (track vendors)
- [ ] Add Transaction entity (track stock movements)
- [ ] Implement barcode/SKU support
- [ ] Add product images
- [ ] Export reports (PDF/Excel)

### Phase 3: Notifications
- [ ] Email alerts for low stock
- [ ] SMS notifications
- [ ] Push notifications

### Phase 4: Analytics
- [ ] Sales analytics dashboard
- [ ] Inventory trends
- [ ] Category performance
- [ ] Revenue calculations

### Phase 5: Microservices Migration
- [ ] Split into separate services:
  - Product Service
  - User Service
  - Category Service
  - Notification Service
- [ ] Add API Gateway
- [ ] Implement service discovery

---

## 📖 Learning Resources

### Concepts Covered
1. **Spring Boot Basics**: Controllers, Services, Repositories
2. **Spring Data JPA**: Custom queries, relationships
3. **Spring Security**: BCrypt, authentication config
4. **AOP**: Logging, transactions, security monitoring
5. **Design Patterns**: Layered architecture, DTOs, separation of concerns
6. **Database**: PostgreSQL, entity relationships, JPQL

### Key Learnings
- **Why Service Layer**: Separation of business logic from HTTP handling
- **Why @Transactional**: Ensures data consistency and rollback on errors
- **Why DTOs**: Separates internal models from API contracts
- **Why AOP**: Removes cross-cutting concerns from business logic
- **Why BCrypt**: Industry-standard password security

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Baeldung Tutorials
- Stack Overflow Community

## 🔄 Version History

### v1.0.0 (Current)
- ✅ Product CRUD operations
- ✅ Category management
- ✅ User signup with encryption
- ✅ Custom queries (search, filter, analytics)
- ✅ Service layer architecture
- ✅ AOP for logging and monitoring
- ✅ Transaction management

### Future Versions
- v2.0.0: Complete authentication with JWT
- v3.0.0: Supplier and Transaction entities
- v4.0.0: Microservices architecture
---

**Happy Coding! 🚀**
