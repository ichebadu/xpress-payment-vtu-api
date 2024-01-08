package com.iche.xpresspayapi.configuration.networkConfig;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.xpresspayapi.dto.request.airtimeVTU.AirtimeVTUPurchaseRequestAPI;
import com.iche.xpresspayapi.exceptions.BillersRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class BillerServiceRequestConfig {

    @Value("${biller.api.private.key}")
    private String billerPrivateKey;

    @Value("${biller.api.public.key}")
    private String billerPublicKey;

    public Map<String, String> billerRequestHeaderConfig(AirtimeVTUPurchaseRequestAPI airtimeVTUPurchaseRequestAPI){
        Map<String, String> headers = new HashMap<>();
        String jsonRequestFormatter = requestDeserializer(airtimeVTUPurchaseRequestAPI);
        headers.put("PaymentHash", calculateHMAC512(jsonRequestFormatter));
        headers.put("Authorization", "Bearer " + billerPublicKey);
        headers.put("Channel", "API");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private static String requestDeserializer(AirtimeVTUPurchaseRequestAPI airtimeVTUPurchaseRequestAPI){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(airtimeVTUPurchaseRequestAPI);
        } catch (JsonProcessingException e){
            throw  new BillersRequestException("can not convert airtime request to json");
        }
    }

    private String calculateHMAC512(String payload){
        String HMAC_SHA512 = "HmacSHA512";
        SecretKeySpec secretKeySpec = new SecretKeySpec(billerPrivateKey.getBytes(), HMAC_SHA512);
        Mac mac = null;
        try{
            mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);
            return Hex.encodeHexString(mac.doFinal(payload.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e){
            throw new BillersRequestException("can not convert airtime request to json");
        }
    }

}