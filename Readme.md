# Xpress-pay API

This project is a Spring Boot application named Xpress-pay, providing REST APIs for various functionalities. The application is designed to handle airtime purchases through a biller service. It includes user authentication, registration, and airtime purchase APIs.

## Application Structure

### 1. Configuration

#### 1.1 Swagger Configuration
The `SwaggerConfig` class in the `com.iche.xpresspayapi.configuration` package configures Swagger documentation for the REST APIs. It includes information such as API title, description, version, contact details, and license. Additionally, it specifies the security requirements for Bearer Authentication.

#### 1.2 Security Configuration
The `SecurityConfig` class, located in the `com.iche.xpresspayapi.configuration` package, defines security configurations for the application. It includes white-listed URLs, JWT authentication setup, and logout handling. The `WHITE_LIST_URLS` array contains URLs that are allowed without authentication.

#### 1.3 Mail Configuration
The `MailConfig` class in the `com.iche.xpresspayapi.configuration` package configures the JavaMailSender for sending emails. It reads mail-related properties from `application.yml`, such as host, port, username, and password.

#### 1.4 Application Configuration
The `ApplicationConfig` class configures various beans required by the application. Notable configurations include setting up an ObjectMapper with JavaTimeModule, defining a PasswordEncoder, creating a RestTemplate, and configuring AuthenticationManager and AuthenticationProvider.

#### 1.5 Biller Service Request Configuration
The `BillerServiceRequestConfig` class in the `com.iche.xpresspayapi.configuration.networkConfig` package handles the configuration for making requests to the biller service. It calculates an HMAC512 hash for the payload and sets necessary headers for authorization.

### 2. Controller

#### 2.1 Authentication Controller
The `AuthController` class in the `com.iche.xpresspayapi.controller` package provides REST APIs for user authentication, including verification, registration, and login.

**Verification API:**
- **Endpoint:** `POST /api/v1/xpress-pay-api/auth/verification`
- **Description:** Used to verify a user's email using OTP.
- **Request Body:**
  ```json
  {
    "email": "chukwu.iche@gmail.com",
    "otp": "f829"
  }
