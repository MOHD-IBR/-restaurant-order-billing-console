# Restaurant Order & Billing Console

Middle-level Enterprise Application Development project.

## Technology Stack
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- MySQL

## Features
- Menu Management (Add, Edit, Delete items)
- Take new orders
- Generate bill with tax calculation (5%)
- View order history
- Printable bill

## How to Run

### 1. Database Setup
- Install MySQL
- Create a user or use root
- Update `src/main/resources/application.properties` if needed:
  - spring.datasource.username=root
  - spring.datasource.password=yourpassword

The database `restaurant_db` will be created automatically.

### 2. Run the Project
```bash
mvn spring-boot:run
```

Or open in IntelliJ / Eclipse and run `RestaurantApplication.java`

### 3. Open in Browser
```
http://localhost:8080
```

## Project Structure
```
src/main/java/com/restaurant/
├── RestaurantApplication.java
├── model/          → MenuItem, Order, OrderItem
├── repository/     → JPA Repositories
├── service/        → Business logic
└── controller/     → Web controllers

src/main/resources/
├── templates/      → Thymeleaf HTML pages
└── application.properties
```

## Modules
1. **Dashboard** - Overview of menu items and orders
2. **Menu Management** - CRUD for food items
3. **New Order** - Select items, enter customer & table, generate bill
4. **Order History** - View past orders and bills

## Notes
- Tax is fixed at 5%
- No login system (kept simple for middle level)
- Make sure MySQL is running before starting the application
