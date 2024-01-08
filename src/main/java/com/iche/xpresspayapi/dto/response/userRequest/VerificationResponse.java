package com.iche.xpresspayapi.dto.response.userRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VerificationResponse {
    private Long userId;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;
}
