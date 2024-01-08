package com.iche.xpresspayapi.dto.response.userRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;

}
