package com.iche.xpresspayapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.xpresspayapi.dto.request.userRequest.RegistrationRequest;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.service.securityService.JwtAuthenticationFilter;
import com.iche.xpresspayapi.service.tokenService.TokenService;
import com.iche.xpresspayapi.service.userService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
        registrationRequest =  new RegistrationRequest();
        registrationRequest.setEmail("chiorlujack@gmail.com");
        registrationRequest.setPassword("12345");
        registrationRequest.setFirstName("jwsven");
        registrationRequest.setPhoneNumber("09876543456");
        registrationRequest.setLastName("lasty");

    }

    @Test
    void registerAnyUser() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("http://localhost:8011/api/v1/xpress-pay-api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)));

        resultActions.andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void verifyUser() {
    }

    @Test
    void login() {
    }


}