package com.iche.xpresspayapi.service.tokenService;

import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.model.Token;
import com.iche.xpresspayapi.model.Users;

public interface TokenService {
    APIResponse<String> verifyUserOtp(String email, String otp);

    void sendOtp(Users user, String otp, Token newConfirmationToken);

    boolean isOtpExpired(Token confirmationToken);

    void saveOtp(Token confirmationToken);

    Token generateOtp(Users user);

    String verifyToken(String token);
}
