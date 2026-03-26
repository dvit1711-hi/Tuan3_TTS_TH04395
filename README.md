# Task Management API

API quản lý người dùng, project và task được xây dựng bằng **Spring Boot**, **Spring Security**, **JWT**, **Spring Data JPA**, **SQL Server** và **Swagger/OpenAPI**.

---

## Công nghệ sử dụng

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- SQL Server
- Maven
- Swagger / OpenAPI

---

## Chức năng chính

- Đăng ký tài khoản
- Đăng nhập lấy JWT token
- Phân quyền theo role
- Manager tạo project
- Quản lý task:
  - tạo task
  - gán task cho user
  - cập nhật trạng thái task
  - xem task theo user
  - xem task theo project
  - xem task của user đang đăng nhập

---

## Cấu trúc profile

Project sử dụng 2 môi trường cấu hình:

- `dev`: chạy local để code và test
- `prod`: dùng khi deploy

### `application.yml`

```yaml
spring:
  profiles:
    active: dev

```
### `application-dev.yml`

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=TTS_TH04395;encrypt=true;trustServerCertificate=true;
    username: sa
    password: 123
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver

  jpa:
    database-platform: org.hibernate.dialect.SQLServerDialect
    hibernate:
      ddl-auto: update
    show-sql: true
```

### `application-prod.yml`

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://prod-server:1433;databaseName=TTS_TH04395
    username: prod_user
    password: prod_pass
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver

  jpa:
    database-platform: org.hibernate.dialect.SQLServerDialect
    hibernate:
      ddl-auto: validate
    show-sql: false

```
