# Warehouse Inventory System

A robust Spring Boot backend designed to handle the core logic of warehouse management. This system tracks products, their specific variants, real-time stock levels, and pricing history.

It’s built with a clean, layered architecture and modern Java standards to ensure scalability and maintainability.

---

##  Quick Start

### Prerequisites

* **Java 21** (Minimum 17+)
* **Maven** (or use the provided `./mvnw`)
* **MySQL** instance

### Installation & Setup

1. **Clone the project**
```bash
git clone https://github.com/akayasha/warehouse_inventory_test.git
cd warehouse_inventory_test

```


2. **Configure the Database**
   Update `src/main/resources/application.properties` with your local MySQL credentials. Make sure to create the `warehouse_db` schema first.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/warehouse_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password

```


3. **Run the Application**
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run

```


4. **Interactive Documentation**
   Once started, explore the API and test endpoints via Swagger UI:
   `http://localhost:8080/swagger-ui/index.html`

---

##  Architecture & Design

The project follows a **Layered Architecture** (Controller → Service → Repository) to keep business logic separated from the web and data layers.

* **Mapping:** Powered by **MapStruct** to cleanly separate JPA Entities from API Data Transfer Objects (DTOs).
* **Validation:** Strict input validation using **Jakarta Validation** (e.g., `@NotBlank`, `@Min`).
* **Safety:** Custom global exception handling ensures that errors return a consistent `ApiResponse` format rather than messy stack traces.
* **Auditing:** Automatically tracks `createdAt` and `updatedAt` for every record using JPA Auditing.
* **Pagination:** All list endpoints are paginated to efficiently handle large datasets.
* **Testing:** Comprehensive unit and integration tests ensure reliability and ease of maintenance.
* **Build:** The project is built with Maven, and the `pom.xml` is configured to manage dependencies and plugins effectively.
* **Concurrency:** The service layer is designed to handle concurrent updates to stock levels, preventing race conditions and ensuring data integrity.
* **Error Handling:** Custom exceptions like `ResourceNotFoundException` and `InsufficientStockException` provide clear error messages and appropriate HTTP status codes.
* **DTOs:** Separate DTO classes for requests and responses ensure that the API contract is clear and decoupled from the internal data model.
* **Mapping:** MapStruct is used to convert between entities and DTOs, keeping the code clean and maintainable.
* **Cascading:** JPA cascading is configured to allow for nested creation and updates of items, variants, stock, and prices in a single API call.
* **Documentation:** OpenAPI 3 annotations provide clear API documentation accessible via Swagger UI.
* **Security:** While not implemented in this version, the architecture allows for easy integration of Spring Security for authentication and authorization in the future.
* **Database:** MySQL is used for persistence, with JPA/Hibernate handling the ORM layer. The schema is designed to efficiently manage complex product structures and inventory relationships.


---

##  Database Schema

The system uses a relational model to handle complex product structures:

* **Items:** The core product definition (e.g., "Smartphone").
* **Variants:** Specific iterations (e.g., "iPhone 15, Blue, 256GB"). Each variant has a unique **SKU**.
* **Stock:** A 1:1 relationship with variants to manage quantities.
* **Price:** A 1:N relationship allowing for multiple price points per variant, with an "active" flag for current pricing.

---

##  API Guide

### 1. Item Management

* **Create Item:** `POST /api/items` — Supports nested creation of variants, stock, and prices in one go.
* **List Items:** `GET /api/items` — Fully paginated to handle large inventories.
* **Get/Update/Delete:** `GET|PUT|DELETE /api/items/{id}`

### 2. Variant Operations

* **Add Variant:** `POST /api/variants/item/{itemId}` — Add new options to an existing item.
* **SKU Search:** `GET /api/variants/search?sku=...` — Quick lookup for specific inventory units.

### 3. Inventory Control

* **Restock:** `POST /api/inventory/{variantId}/add` — Increase stock quantity.
* **Sell:** `POST /api/inventory/{variantId}/sell` — Decrease stock.
> **Note:** This will throw an `InsufficientStockException` if you try to sell more than what is available.



---

##  Testing & Tools

**Run the test suite:**

```bash
./mvnw test

```

The project includes unit tests using **JUnit 5** and **Mockito**, alongside integration tests using **MockMvc** to verify endpoint behavior.

**Key Tech Stack:**

* Spring Boot (Web, Data JPA, Validation)
* Lombok (Boilerplate reduction)
* MapStruct (Object mapping)
* MySQL (Persistence)
* SpringDoc (OpenAPI 3/Swagger)

---

## Design Assumptions

* **Identity:** All entities use **UUIDs** as primary keys for better security and distributed system compatibility.
* **Deletes:** Standard hard deletes are implemented (Soft delete can be added if history is required).
* **Integrity:** Database constraints ensure SKUs remain unique across the entire warehouse.

---
