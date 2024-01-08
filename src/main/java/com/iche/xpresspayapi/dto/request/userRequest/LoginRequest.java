package com.iche.xpresspayapi.dto.request.userRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Column(name="email", nullable = false)
    @NotBlank(message = "Enter your email")
    private String email;
    @Column(name="password", nullable = false)
    @NotBlank(message = "Enter your password")
    private String password;
}
