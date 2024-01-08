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
**Response:**
```json
  {
  "message": "processed Successfully",
  "time": "2024-01-08 11:37:14 pm",
  "data": "activate"
  }



### Registration API:

**Endpoint:** `POST /api/v1/xpress-pay-api/auth/registration`

**Description:** Used to register a new user.

**Request Body:**
```json
{
  "email": "chukwu.iche@gmail.com",
  "phoneNumber": "07066803409",
  "password": "1234",
  "first_name": "ichebadu",
  "last_name": "chukwu"
}

Response
{
  "message": "Processed successfully",
  "time": "2024-01-08 11:36:44 PM",
  "data": {
    "username": "chukwu.iche@gmail.com",
    "message": "Registration successful"
  }
}

Login API:
Endpoint: POST /api/v1/xpress-pay-api/auth/login

Description: Used for user authentication.

Request Body:

json
Copy code
{
  "email": "chukwu.iche@gmail.com",
  "password": "1234"
}
Response:

json
Copy code
{
  "message": "Processed successfully",
  "time": "2024-01-08 11:37:53 PM",
  "data": {
    "username": "chukwu.iche@gmail.com",
    "email": "chukwu.iche@gmail.com",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoiY2h1a3d1LmljaGVAZ21haWwuY29tIiwiaWF0IjoxNzA0NzUzNDczLCJleHAiOjE3MDQ4Mzk4NzN9.I3z22wnK2zTk4sH_KUntTsgWRxj6lVTRCEeTqvTIAiE",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaHVrd3UuaWNoZUBnbWFpbC5jb20iLCJpYXQiOjE3MDQ3NTM0NzMsImV4cCI6MTcwNTAxMjY3M30.bhwcDAyMD8M6z7L2hBF2..."
  }
}
Airtime Controller
The AirtimeController class in the com.iche.xpresspayapi.controller package provides REST APIs for airtime purchase.

Purchase Airtime API:
Endpoint: POST /api/v1/vtu/airtime/purchase

Description: Used to purchase airtime.

Request Body:

json
Copy code
{
  "phone_number": "08033333333",
  "amount": 5000,
  "network_provider": "MTN"
}
Response:

json
Copy code
{
  "message": "Processed successfully",
  "time": "2024-01-08 11:39:09 PM",
  "data": {
    "amount": 5000,
    "phone_number": "08033333333",
    "transaction_status": "SUCCESSFUL"
  }
}
External Configurations
The application utilizes external configuration properties from the application.yml file. Key configurations include biller API keys, JWT settings, database connection details, server port, and email properties.

yaml
Copy code
# Example application.yml configurations
biller:
  api:
    private:
      key: "hrYZT8t4ih8oVmPrtjDZCB4aUP5UGDTT_CVASPRV"
    public:
      key: "keD5z52EpvurSzPu9tJrJ2VMZMauzpK9_CVASPUB"

jwt:
  expiration:
    access-token: 86400000
    refresh-token: 259200000
  secret-key: "0S07aPkGwOoB1GLEtYPIHaeyQ8VLkKisUMPrKtPt6KQ3NXB6giH2AiXotG5oRDzO4XpjGaXcw6TomIYUbxVkew=="

spring:
  datasource:
    driver-class-name: "org.postgresql.Driver"
    url: "jdbc:postgresql://localhost:5432/xpress_pay_db"
    username: "postgres"
    password: "root"
Running the Application
Ensure that you have the required configurations set in the application.yml file. Run the application using your preferred method, such as Maven or an IDE.


# Example Maven command
mvn spring-boot:run
The application will start on the specified port (default is 8011) and can be accessed through the defined URLs.

API Documentation
Swagger documentation is available at http://localhost:8011/swagger-ui.html. This documentation provides details on the available REST APIs, allowing easy testing and exploration of the endpoints.