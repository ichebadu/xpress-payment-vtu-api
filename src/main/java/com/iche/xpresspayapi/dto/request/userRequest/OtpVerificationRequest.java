package com.iche.xpresspayapi.dto.request.userRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationRequest {
    @NotBlank(message = "Email address must not be empty")
    @Size(min = 5, max = 50)
    private String email;
    @NotBlank(message = "OTP must not be empty")
    private String otp;
}
