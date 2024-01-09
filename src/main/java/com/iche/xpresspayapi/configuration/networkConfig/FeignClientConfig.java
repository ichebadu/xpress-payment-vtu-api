package com.iche.xpresspayapi.configuration.networkConfig;


import com.iche.xpresspayapi.dto.response.airtime.AirtimeVTUResponseAPI;
import com.iche.xpresspayapi.exceptions.BillersRequestException;
import com.iche.xpresspayapi.model.AirtimeVTUTransaction;
import com.iche.xpresspayapi.utils.EndpointUtils;
import com.iche.xpresspayapi.utils.ResponseCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class FeignClientConfig {
//    @Value("${biller.private.api.key}")
//    private String merchantApiKey;

//    @Bean
//    public RequestInterceptor requestInterceptor(){
//        return requestTemplate -> {
//            requestTemplate.header("Authorization", "Bearer " + merchantApiKey);
//            requestTemplate.header("Content-type", "application/json");
//            requestTemplate.header("Channel", "API");
//            requestTemplate.header("PaymentHash", "GENERATE_HMAC");
//        };
//    }

//    Map<String, String> headers = billerServiceRequestConfig.billerRequestHeaderConfig(airtimeVTUPurchaseRequestAPI);
//    ResponseEntity<?> airtimeVTUResponseAPI =  vtuAirtimeRequestHandler.sendNetworkPostRequest(EndpointUtils.VTU_AIRTIME_URL, airtimeVTUPurchaseRequestAPI, AirtimeVTUResponseAPI.class, headers);
//
//    AirtimeVTUTransaction airtimeVTUTransaction = AirtimeVTUTransaction.builder()
//            .amount(airtimeVTUPurchaseRequestAPI.getDetails().getAmount())
//            .phoneNumber(airtimeVTUPurchaseRequestAPI.getDetails().getPhoneNumber())
//            .user(user)
//            .build();
//
//                        if (!ObjectUtils.isEmpty(airtimeVTUResponseAPI)) {
//        if (airtimeVTUResponseAPI.getStatusCode().equals(ResponseCode.BILLER_SUCCESS_CODE)) {



//    public ResponseEntity<?> sendNetworkPostRequest(String url, Object requestPayload, Class<?> responseClass, Map<String, String> headers){
//        try {
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setAll(headers);
//            HttpEntity<Object> requestEntity = new HttpEntity<>(requestPayload, httpHeaders);
//            ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, requestEntity, responseClass);
//            return responseEntity;
//        } catch (Exception e){
//            throw new BillersRequestException("network request failed");
//        }
//    }
}
