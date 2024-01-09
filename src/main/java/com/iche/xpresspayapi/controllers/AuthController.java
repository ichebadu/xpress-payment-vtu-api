package com.iche.xpresspayapi.controllers;

import com.iche.xpresspayapi.dto.request.userRequest.LoginRequest;
import com.iche.xpresspayapi.dto.request.userRequest.OtpVerificationRequest;
import com.iche.xpresspayapi.dto.request.userRequest.RegistrationRequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.LoginResponse;
import com.iche.xpresspayapi.dto.response.userRequest.RegistrationResponse;
import com.iche.xpresspayapi.service.tokenService.TokenService;
import com.iche.xpresspayapi.service.userService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.iche.xpresspayapi.utils.EndpointUtils.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_BASE_URL)
@Slf4j
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;



    @Operation(
            summary = "User Registration REST API",
            description = "User Registration REST API is used to register a new user"
    )
    @ApiResponse(responseCode = "200", description = "Http status 200 SUCCESS")
    @PostMapping(REGISTRATION)
    public ResponseEntity<APIResponse<RegistrationResponse>> registerAnyUser(@Valid
            @RequestBody RegistrationRequest registrationRequest
    ){
        return new ResponseEntity<>(userService.registerUser(registrationRequest), HttpStatus.CREATED);
    }
    @PostMapping(VERIFICATION)
    @Operation(
            summary = "User Verification REST API",
            description = "User Verification REST API is used to verify user's email using OTP"
    )
    @ApiResponse(responseCode = "200", description = "Http status 200 SUCCESS")
    public ResponseEntity<APIResponse<String>> verifyUser(
            @RequestBody @Valid OtpVerificationRequest otpVerificationRequest
    ){
        return new ResponseEntity<>(tokenService.verifyUserOtp(otpVerificationRequest),HttpStatus.OK);
    }
    @PostMapping(LOGIN)
    @Operation(
            summary = "User Login REST API",
            description = "User Login REST API is used for user authentication"
    )
    @ApiResponse(responseCode = "200", description = "Http status 200 SUCCESS")
    public ResponseEntity<APIResponse<LoginResponse>>login(@Valid
            @RequestBody LoginRequest loginRequest
    ){
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

}
