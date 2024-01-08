package com.iche.xpresspayapi.service.airtimeService;

import com.iche.xpresspayapi.dto.request.userRequest.UserPurchaseAirtimeVTURequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.userRequest.PurchaseAirtimeResponse;

public interface VTUAirtimeService {
    APIResponse<PurchaseAirtimeResponse> purchaseVTUAirtime(UserPurchaseAirtimeVTURequest userPurchaseAirtimeVTURequest);
}
