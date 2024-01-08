package com.iche.xpresspayapi.configuration.networkConfig;

import com.iche.xpresspayapi.exceptions.BillersRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class VTUAirtimeRequestHandler {
    private final RestTemplate restTemplate;
    public Object sendNetworkPostRequest(String url, Object requestPayload, Class<?> responseClass, Map<String, String> headers){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAll(headers);
            HttpEntity<Object> requestEntity = new HttpEntity<>(requestPayload, httpHeaders);
            ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, requestEntity, responseClass);
            return responseEntity.getBody();
        } catch (Exception e){
            throw new BillersRequestException("network request failed");
        }
    }
}
