package com.iche.xpresspayapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.xpresspayapi.dto.request.userRequest.RegistrationRequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.RegistrationResponse;
import com.iche.xpresspayapi.enums.Role;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.service.securityService.JwtAuthenticationFilter;
import com.iche.xpresspayapi.service.tokenService.TokenService;
import com.iche.xpresspayapi.service.userService.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.iche.xpresspayapi.utils.EndpointUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private  UserService userService;
    @MockBean
    private  TokenService tokenService;

    private Users user;
    private RegistrationRequest registrationRequest;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init(){
//         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        registrationRequest =  new RegistrationRequest();
        registrationRequest.setEmail("chiorlujack@gmail.com");
        registrationRequest.setPassword("12345");
        registrationRequest.setFirstName("jwsven");
        registrationRequest.setPhoneNumber("09876543456");
        registrationRequest.setLastName("lasty");
//        loginRequest =  new LoginRequest(EMAIL,"1234");
//        responseFromUser = new ResponseFromUser(loginRequest.getEmail(),"jwsven",true,new Date().toString(),"");
//        otpValidateRequest =new OtpValidateRequest(RandomValues.generateRandom().substring(0,4),loginRequest.getEmail());

    }

    @Test
    void registerAnyUser() throws Exception {

//                given(userService.registerUser(registrationRequest)).willAnswer((invocationOnMock -> {
//            APIResponse<RegistrationRequest> registerRequest =invocationOnMock.getArgument(0);
//            return "processed Successfully";
//        }));

        ResultActions resultActions = mockMvc.perform(post("http://localhost:8011/api/v1/xpress-pay-api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)));

        resultActions.andExpect(status().isCreated())
              //  .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(registrationRequest.getEmail())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void verifyUser() {
    }

    @Test
    void login() {
    }


}