
# ✈️ Air Ticket Booking System

## 🧾 Giới thiệu

Đây là một hệ thống đặt vé máy bay được xây dựng với mục đích quản lý người dùng, chuyến bay và các giao dịch đặt vé. Người dùng có thể đăng nhập, đăng ký, đặt vé và nhận thông tin vé qua email kèm mã QR. Hệ thống áp dụng các cơ chế bảo mật tiên tiến và hỗ trợ đăng nhập bằng Google.

---

## 🛠️ Công nghệ sử dụng

- **Spring Boot** – khung ứng dụng chính
- **Spring Security** – bảo mật người dùng và phân quyền
- **Spring Security OAuth2** – hỗ trợ đăng nhập với Google
- **JPA & Hibernate** – ORM ánh xạ cơ sở dữ liệu
- **Lombok** – giảm mã lặp (get/set/toString/constructor…)
- **JWT (JSON Web Token)** – xác thực người dùng qua token
- **Bean Validation (JSR-303)** – kiểm tra ràng buộc dữ liệu
- **JUnit 5** – kiểm thử đơn vị
- **QRCode Generator** – tạo mã QR chứa thông tin vé
- **JavaMailSender** – gửi email thông báo

---

## 📦 Kiến trúc hệ thống

### 1. **Entity Layer**
> Mapping các entity theo quan hệ 1-1, 1-n, n-n.

Các entity chính:
- `User`
- `Role`
- `Permission`
- `Booking`
- `Flight`
- `InvalidateToken`

### 2. **Repository Layer**
> Mỗi entity đều có repository mở rộng từ `JpaRepository` để truy xuất dữ liệu.

### 3. **DTO Layer**
> Tách biệt dữ liệu yêu cầu và phản hồi:
- `Request DTO`
- `Response DTO`

### 4. **Mapper Layer**
> Sử dụng `Mapper` để chuyển đổi giữa `DTO` và `Entity`.

### 5. **Service Layer**
> Chia thành Interface và Implementation. Một số chức năng chính:

#### 📌 **Booking Service**
- Đặt vé (generate QR & gửi email)
- Xem thông tin booking theo user
- CRUD cơ bản

#### 📌 **Flight, Role, Permission Service**
- CRUD
- Bảo vệ bằng `@PreAuthorize("hasRole('ADMIN')")`

#### 📌 **User Service**
- CRUD
- Cập nhật thông tin bản thân với `@PostAuthorize("returnObject.userName == authentication.name")`
- Cung cấp `UserDetails` cho Spring Security
- Mapping `Role` -> `GrantedAuthority`

#### 📌 **Authentication Service**
- Login
- Logout
- Refresh Token
- Login bằng Google (OAuth2)

#### 📌 **Email Service**
- Gửi email xác nhận vé

#### 📌 **QR Service**
- Tạo mã QR chứa thông tin vé

#### 📌 **JWT Service**
- Tạo, xác minh token
- Kiểm tra hết hạn
- Xây dựng scope, vv

---

## 🔐 Bảo mật

### 🔧 Cấu hình JWT & Security

- **`JwtConfig`**: Giải mã token, xử lý entrypoint lỗi
- **`SecurityConfig`**: Cấu hình:
  - `PasswordEncoder` (BCrypt)
  - `DaoAuthenticationProvider`
  - `SecurityFilterChain`: định nghĩa endpoint công khai, xác thực theo role
  - `CorsConfigurationSource`: giới hạn frontend truy cập

### 🧨 Exception Handling
- `AppException` extends `RuntimeException`
- `ErrorCode` enum định nghĩa mã lỗi
- `GlobalExceptionHandler` dùng `@ControllerAdvice` xử lý:
  - `BadCredentialsException`
  - `AccessDeniedException`
  - `AppException`
  - `ValidationException`, v.v.

---

## 🧪 Kiểm thử

- Sử dụng **JUnit 5** để viết unit test cho các service và controller quan trọng.

---

## 📚 Tài liệu tham khảo

- [Spring Framework Docs](https://spring.io/projects/spring-boot)
- [Devteria YouTube Channel](https://www.youtube.com/@DevteriaChannel)
- Tài liệu nội bộ: `Spring.doc`

---

## 👨‍💻 Tác giả

- **Họ tên**: Nguyễn Chí Tâm  
- **Trường**: Đại học Cần Thơ  
- **Email**:  
  - tamn0443@gmail.com  
  - tam1402.dev.ct@gmail.com  
