package com.iche.xpresspayapi.service.userService;

import com.iche.xpresspayapi.dto.request.userRequest.LoginRequest;
import com.iche.xpresspayapi.dto.request.userRequest.RegistrationRequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.LoginResponse;
import com.iche.xpresspayapi.dto.response.userRequest.RegistrationResponse;
import com.iche.xpresspayapi.model.Users;

public interface UserService {
    APIResponse<RegistrationResponse> registerUser(RegistrationRequest registrationRequest);

    APIResponse<LoginResponse> login(LoginRequest loginRequest);

    Users getUserByEmail(String email);
}
