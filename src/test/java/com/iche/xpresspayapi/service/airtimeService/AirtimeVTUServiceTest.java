package com.iche.xpresspayapi.service.airtimeService;
import com.iche.xpresspayapi.configuration.networkConfig.BillerServiceRequestConfig;
import com.iche.xpresspayapi.configuration.networkConfig.VTUAirtimeRequestHandler;
import com.iche.xpresspayapi.dto.request.airtimeVTU.AirtimeVTUPurchaseRequestAPI;
import com.iche.xpresspayapi.dto.request.airtimeVTU.Details;
import com.iche.xpresspayapi.dto.request.userRequest.LoginRequest;
import com.iche.xpresspayapi.dto.request.userRequest.UserPurchaseAirtimeVTURequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.airtime.AirtimeVTUResponseAPI;
import com.iche.xpresspayapi.dto.response.userRequest.PurchaseAirtimeResponse;

import com.iche.xpresspayapi.model.NetworkProvider;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.repository.AirtimeVTUTransactionRepository;
import com.iche.xpresspayapi.repository.NetworkProviderRepository;
import com.iche.xpresspayapi.service.userService.UserService;
import com.iche.xpresspayapi.utils.BillerTransactionIdGenerator;
import com.iche.xpresspayapi.utils.ResponseCode;
import com.iche.xpresspayapi.utils.UserVerification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AirtimeVTUServiceTest {

    @Mock
    private AirtimeVTUTransactionRepository airtimeVtuTransactionRepository;

    @Mock
    private UserService userService;

    @Mock
    private NetworkProviderRepository networkProviderRepository;

    @Mock
    private BillerServiceRequestConfig billerServiceRequestConfig;

    @Mock
    private VTUAirtimeRequestHandler vtuAirtimeRequestHandler;

    @Mock
    private UserVerification userVerification;

    @InjectMocks
    private AirtimeVTUService airtimeVTUService;


    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String email = "chukwu.iche@gmail.com";
        String password = "1234";
        LoginRequest loginRequest  =new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(passwordEncoder.encode(password));


        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        securityContext.setAuthentication(authentication);
    }

    @Test
    void purchaseVTUAirtime_WithValidRequest_ShouldReturnSuccessfulResponse() {
        UserPurchaseAirtimeVTURequest request = new UserPurchaseAirtimeVTURequest("12345678901", BigDecimal.TEN, "SomeProvider");

        Users user = Users.builder().status(true).build();
        when(userService.getUserByEmail(anyString())).thenReturn(user);

        NetworkProvider networkProvider = NetworkProvider.builder().uniqueCode("ProviderCode").build();
        when(networkProviderRepository.findNetworkProviderByNetworkProviderNameEqualsIgnoreCase(anyString())).thenReturn(networkProvider);

        Map<String, String> headers = Map.of("header1", "value1", "header2", "value2");
        when(billerServiceRequestConfig.billerRequestHeaderConfig(any())).thenReturn(headers);

        AirtimeVTUPurchaseRequestAPI airtimeVTUPurchaseRequestAPI = createMockRequest(request);
        when(vtuAirtimeRequestHandler.sendNetworkPostRequest(
                any(), any(), eq(AirtimeVTUResponseAPI.class), any())).thenReturn(createMockResponse());

        APIResponse<PurchaseAirtimeResponse> response = airtimeVTUService.purchaseVTUAirtime(request);

        assertNotNull(response);

        PurchaseAirtimeResponse responseData = response.getData();
        assertNotNull(responseData);

        assertEquals("12345678901", responseData.getPhoneNumber());
        assertEquals("SUCCESSFUL", responseData.getTransactionStatus());

        verify(airtimeVtuTransactionRepository, times(1)).save(any());
    }

    private AirtimeVTUPurchaseRequestAPI createMockRequest(UserPurchaseAirtimeVTURequest request) {
        BigDecimal bigDecimal = new BigDecimal(1000);

        return AirtimeVTUPurchaseRequestAPI.builder()
                .requestId(BillerTransactionIdGenerator.generateTransactionId())
                .uniqueCode("ProviderCode")
                .details(Details.builder()
                        .amount(bigDecimal)
                        .phoneNumber("08033333333")
                        .build())
                .build();
    }

    private AirtimeVTUResponseAPI createMockResponse() {
        return AirtimeVTUResponseAPI.builder()
                .responseCode(ResponseCode.BILLER_SUCCESS_CODE)
                .build();
    }
}
