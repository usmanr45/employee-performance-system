# 👨‍💼 Employee Performance System

A full-stack Spring Boot application to manage employee data, departments, projects, and performance reviews.  
Provides filtering and detailed views via REST APIs.

---

## ✅ Features

- Manage **Employees**, **Departments**, **Projects**, and **Performance Reviews**
- Get detailed employee info with latest 3 reviews and project roles
- Filter employees by:
  - Review score
  - Review date
  - Department name (contains)
  - Project name (contains)
- Global exception handling
- Logging for service and controller layers

---

## 🛠️ Tech Stack

| Layer         | Technology                    |
|---------------|-------------------------------|
| Backend       | Java 17, Spring Boot 3.x       |
| Database      | MySQL                         |
| ORM           | JPA (Hibernate)               |
| Build Tool    | Maven                         |
| Logging       | SLF4J + Logback               |
| Testing       | JUnit 5                       |
| Tooling       | IntelliJ IDEA / VS Code       |
| API Docs      | Swagger (can be added)        |

---

## ⚙️ Setup Instructions

### 📦 Prerequisites

- Java 17+
- Maven
- MySQL running locally
- IntelliJ IDEA or any IDE of your choice

---

### 🔄 1. Clone the Repository

```bash
git clone https://github.com/usmanr45/employee-performance-system.git
cd employee-performance-system
```

### 🛢️ 2. Create MySQL Database
```sql
CREATE DATABASE employee_db;
```

### 🔧 3. Configure application.yml
File: src/main/resources/application.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employee_db
    username: 
    password: 
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 🚀 4. Run the Application
```
mvn spring-boot:run

Or run from your IDE (e.g., IntelliJ)
```

### 🔍 5. API Endpoints
```
🔹 Filter Employees
GET /api/employees/filter
Query Params (optional):

score (Double)

reviewDate (ISO format: YYYY-MM-DD)

departments (List of names)

projects (List of names)

🔹 Get Employee Details by ID
GET /api/employees/{id}
Returns full employee info including:

Basic info

Department

Assigned projects and roles

Last 3 performance reviews

```

### 🧪 Sample Test Data (auto-loaded from data.sql)
```
Tables: departments, employees, projects, reviews, assignments
Check file: src/main/resources/data.sql
```

### 👨‍💻 Author
Usman
GitHub: @usmanr45

### 📄 License
```
This project is built for assignment/demo purposes.
```

### 👨‍💻 Author
```
Usman
GitHub: @usmanr45
```
