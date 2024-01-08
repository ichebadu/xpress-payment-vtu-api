package com.iche.xpresspayapi.service.userService;

import com.iche.xpresspayapi.dto.request.userRequest.LoginRequest;
import com.iche.xpresspayapi.dto.request.userRequest.RegistrationRequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.LoginResponse;
import com.iche.xpresspayapi.dto.response.userRequest.RegistrationResponse;
import com.iche.xpresspayapi.enums.Role;
import com.iche.xpresspayapi.exceptions.*;
import com.iche.xpresspayapi.model.Token;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.notificationEvent.registrationEvent.UserRegistrationEvent;
import com.iche.xpresspayapi.repository.UserRepository;
import com.iche.xpresspayapi.service.securityService.JwtService;
import com.iche.xpresspayapi.service.tokenService.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenServiceImpl;
    private final ApplicationEventPublisher publisher;
    private final JwtService jwtService;

    @Override
    public APIResponse<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {
        validateUserExistence(registrationRequest.getEmail());
        System.out.println(registrationRequest.getPassword());
   //     String formattedPhoneNumber = savePhoneNumber(registrationRequest.getPhoneNumber());

                if (isPhoneNumberRegistered(registrationRequest.getPhoneNumber())) {
                    throw new UserAlreadyExistsException("Phone number is already registered.");
                }
        Users user = Users.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getFirstName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
    //            .phoneNumber(formattedPhoneNumber)
                .role(Role.USER)
                .status(false)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();

        var saveUser = userRepository.save(user);

        Token token = tokenServiceImpl.generateOtp(saveUser);
        token.setUser(user);
        tokenServiceImpl.saveOtp(token);
        String otp = token.getOtp();
        log.info(otp);
        try {
            publisher.publishEvent(new UserRegistrationEvent(user, otp));
        } catch (RuntimeException e){
            throw new OtpException("COULD NOT SEND MAIL");
        }

        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .username(user.getUsername())
                .message("Registration Successful")
                .build();
        return new APIResponse<>(registrationResponse);
    }

    @Override
    public APIResponse<LoginResponse> login(LoginRequest loginRequest) {
        Users user = getUserByEmail(loginRequest.getEmail());
        if(user.getStatus().equals(false)){
            throw new UserDisabledException("Password do not match");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new SamePasswordException("Invalid Password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),user.getPassword()
        );

        String jwtAccessToken = jwtService.generateToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponse loginResponse = LoginResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
        return new APIResponse<>(loginResponse);
    }
    public void validateUserExistence(String email){
        if(userRepository.existsByEmail(email)){
            throw new UserAlreadyExistsException(email);
        }
    }
    @Override
    public Users getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
    }
//    private String savePhoneNumber(String phoneNumber) {
//        StringBuilder formatNumber = new StringBuilder();
//
//        for (char digit : phoneNumber.toCharArray()) {
//            if (Character.isDigit(digit)) {
//                formatNumber.append(digit);
//            }
//        }
//
//        if (formatNumber.length() == 11) {
//            char firstDigit = formatNumber.charAt(0);
//
//            if (firstDigit == '0') {
//                String formattedPhoneNumber = "+234" + formatNumber;
//
//                if (isPhoneNumberRegistered(formattedPhoneNumber)) {
//                    throw new UserAlreadyExistsException("Phone number is already registered.");
//                }
//                return formattedPhoneNumber;
//            } else {
//                throw new InvalidCredentialsException("Invalid phone number format. Must start with zero (0).");
//            }
//        } else {
//            throw new InvalidCredentialsException("Invalid phone number format. Must be 11 digits.");
//        }
//    }

    private boolean isPhoneNumberRegistered(String formattedPhoneNumber) {
        return userRepository.existsByPhoneNumber(formattedPhoneNumber);
    }

}
