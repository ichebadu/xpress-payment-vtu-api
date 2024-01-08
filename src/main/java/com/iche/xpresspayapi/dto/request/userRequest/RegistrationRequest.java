package com.iche.xpresspayapi.dto.request.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Email address must not be empty")
    private String email;
    @NotBlank(message = "Email address must not be empty")
    private String phoneNumber;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;
}
