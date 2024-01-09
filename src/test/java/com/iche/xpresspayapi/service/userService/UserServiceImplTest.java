package com.iche.xpresspayapi.service.userService;

import com.iche.xpresspayapi.dto.request.userRequest.LoginRequest;
import com.iche.xpresspayapi.dto.request.userRequest.RegistrationRequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.LoginResponse;
import com.iche.xpresspayapi.dto.response.userRequest.RegistrationResponse;
import com.iche.xpresspayapi.enums.Role;
import com.iche.xpresspayapi.exceptions.UserAlreadyExistsException;
import com.iche.xpresspayapi.exceptions.UserDisabledException;
import com.iche.xpresspayapi.exceptions.UserNotFoundException;
import com.iche.xpresspayapi.model.Token;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.repository.UserRepository;
import com.iche.xpresspayapi.service.securityService.JwtService;
import com.iche.xpresspayapi.service.tokenService.TokenServiceImpl;
import com.iche.xpresspayapi.utils.Validations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenServiceImpl tokenServiceImpl;

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private Validations<RegistrationRequest> registrationRequestValidationUtils;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldReturnRegistrationResponse() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("chukwu.iche@gmail.com");
        registrationRequest.setPassword("password");

        Users savedUser = Users.builder()
                .id(1L)
                .email(registrationRequest.getEmail())
                .role(Role.USER)
                .status(false)
                .build();

        Token userToken = new Token("as4e", savedUser);

        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        doNothing().when(tokenServiceImpl).saveOtp(any(Token.class));
        when(tokenServiceImpl.generateOtp(any(Users.class))).thenReturn(userToken);

        APIResponse<RegistrationResponse> response = userService.registerUser(registrationRequest);

        assertNotNull(response);
        assertEquals("chukwu.iche@gmail.com", response.getData().getUsername());

        verify(userRepository, times(1)).existsByEmail(registrationRequest.getEmail());
        verify(userRepository, times(1)).save(any(Users.class));

        verify(tokenServiceImpl, times(1)).saveOtp(any(Token.class));
        verify(tokenServiceImpl, times(1)).generateOtp(any(Users.class));
    }

    @Test
    void registerUser_WithExistingEmail_ShouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("existing@example.com");
        registrationRequest.setPassword("password");

        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registrationRequest));
    }

    @Test
    void login_WithDisabledUser_ShouldThrowException() {

        String email = "chukwu.iche@gmail.com";
        String password = "1234";
        LoginRequest loginRequest  =new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        Users existingUser = Users.builder()
                .id(1L)
                .email(email)
                .role(Role.USER)
                .status(false)
                .password(passwordEncoder.encode(password))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(existingUser));


      assertThrows(UserDisabledException.class, () -> userService.login(loginRequest));
    }

    @Test
    void login_WithValidCredentials_ShouldReturnLoginResponse() {
        String email = "chukwu.iche@gmail.com";
        String password = "1234";


        Users existingUser = Users.builder()
                .id(1L)
                .email(email)
                .role(Role.USER)
                .status(true)
                .password(password)
                .build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password); // Provide the plain password


        when(passwordEncoder.matches(loginRequest.getPassword(),existingUser.getPassword())).thenReturn(true);

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(existingUser));

        APIResponse<LoginResponse> response = userService.login(loginRequest);
        assertNotNull(response);
        assertEquals(email, response.getData().getEmail());
    }



    @Test
    void getUserByEmail_WithExistingEmail_ShouldReturnUser() {
        String userEmail = "test@example.com";
        Users existingUser = Users.builder()
                .id(1L)
                .email(userEmail)
                .role(Role.USER)
                .status(true)
                .password("validpassword")
                .build();

        when(userRepository.findByEmail(userEmail)).thenReturn(java.util.Optional.of(existingUser));

        Users result = userService.getUserByEmail(userEmail);

        assertNotNull(result);
        assertEquals(userEmail, result.getEmail());
    }

    @Test
    void getUserByEmail_WithNonExistingEmail_ShouldThrowException() {
        String userEmail = "nonexisting@example.com";
        when(userRepository.findByEmail(userEmail)).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(userEmail));
    }

    @Test
    void validateUserExistence_WithExistingEmail_ShouldThrowException() {
        String userEmail = "existing@example.com";
        when(userRepository.existsByEmail(userEmail)).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.validateUserExistence(userEmail));
    }

    @Test
    void validateUserExistence_WithNonExistingEmail_ShouldNotThrowException() {
        String userEmail = "nonexisting@example.com";
        when(userRepository.existsByEmail(userEmail)).thenReturn(false);
        assertDoesNotThrow(() -> userService.validateUserExistence(userEmail));
    }
}
