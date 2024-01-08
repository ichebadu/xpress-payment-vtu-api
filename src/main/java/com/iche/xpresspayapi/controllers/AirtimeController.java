package com.iche.xpresspayapi.controllers;

import com.iche.xpresspayapi.dto.request.userRequest.UserPurchaseAirtimeVTURequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.PurchaseAirtimeResponse;
import com.iche.xpresspayapi.service.airtimeService.AirtimeVTUService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/v1/vtu/airtime", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AirtimeController {
    private final AirtimeVTUService airtimeVtuService;

    @PostMapping("/purchase")
    public ResponseEntity<APIResponse<PurchaseAirtimeResponse>> purchaseAirtime(@RequestBody UserPurchaseAirtimeVTURequest userPurchaseAirtimeVTURequest){
        return new ResponseEntity<>(airtimeVtuService.purchaseVTUAirtime(userPurchaseAirtimeVTURequest), HttpStatus.OK);
    }
}
