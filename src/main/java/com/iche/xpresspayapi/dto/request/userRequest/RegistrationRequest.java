package com.iche.xpresspayapi.dto.request.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @JsonProperty("first_name")
    @NotBlank(message = "first name cannot be empty")
    private String firstName;
    @JsonProperty("last_name")
    @NotBlank(message = "last name cannot be empty")
    private String lastName;
    @NotBlank(message = "Enter your email")
    @Column(name = "email", nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Email address must not be empty")
    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "^[^?*!\\\\/$%^()~<>?\":}{\\[\\]|+=_\\-&#@.,;]+$", message = "Invalid phone number format")
    private String phoneNumber;
    @NotBlank(message = "Enter your password")
    @Column(name = "password", nullable = false)
    @Pattern(regexp = "^[^?*!\\\\/$%^()~<>?\":}{\\[\\]|+=_\\-&#@.,;]+$", message = "Invalid password format")
    private String password;

}
