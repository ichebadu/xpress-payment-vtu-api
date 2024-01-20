package com.iche.xpresspayapi.service.airtimeService;

import com.iche.xpresspayapi.configuration.networkConfig.BillerServiceRequestConfig;
import com.iche.xpresspayapi.configuration.networkConfig.VTUAirtimeRequestHandler;
import com.iche.xpresspayapi.dto.request.airtimeVTU.AirtimeVTUPurchaseRequestAPI;
import com.iche.xpresspayapi.dto.request.airtimeVTU.Details;
import com.iche.xpresspayapi.dto.request.userRequest.UserPurchaseAirtimeVTURequest;
import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.dto.response.airtime.AirtimeVTUResponseAPI;
import com.iche.xpresspayapi.dto.response.userRequest.PurchaseAirtimeResponse;
import com.iche.xpresspayapi.enums.VTUTransactionStatus;
import com.iche.xpresspayapi.exceptions.BillersRequestException;
import com.iche.xpresspayapi.model.AirtimeVTUTransaction;
import com.iche.xpresspayapi.model.NetworkProvider;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.repository.AirtimeVTUTransactionRepository;
import com.iche.xpresspayapi.repository.NetworkProviderRepository;
import com.iche.xpresspayapi.service.userService.UserService;
import com.iche.xpresspayapi.utils.BillerTransactionIdGenerator;
import com.iche.xpresspayapi.utils.EndpointUtils;
import com.iche.xpresspayapi.utils.ResponseCode;
import com.iche.xpresspayapi.utils.UserVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirtimeVTUService implements VTUAirtimeService{

    private final AirtimeVTUTransactionRepository airtimeVtuTransactionRepository;
    private final UserService userService;
    private final NetworkProviderRepository networkProviderRepository;
    private final BillerServiceRequestConfig billerServiceRequestConfig;
    private final VTUAirtimeRequestHandler vtuAirtimeRequestHandler;
    private final UserVerification userVerification;

    @Override
    public APIResponse<PurchaseAirtimeResponse> purchaseVTUAirtime(UserPurchaseAirtimeVTURequest userPurchaseAirtimeVTURequest) {

        try {
            Users user = userService.getUserByEmail(userVerification.getUserEmailFromContext());

            if (user.getStatus()) {
                AirtimeVTUPurchaseRequestAPI airtimeVTUPurchaseRequestAPI = new AirtimeVTUPurchaseRequestAPI();
                airtimeVTUPurchaseRequestAPI.setRequestId(BillerTransactionIdGenerator.generateTransactionId());
                NetworkProvider networkProvider = networkProviderRepository.findNetworkProviderByNetworkProviderNameEqualsIgnoreCase(userPurchaseAirtimeVTURequest.networkProvider());

                if (!ObjectUtils.isEmpty(networkProvider)) {
                    String uniqueCode = networkProvider.getUniqueCode();

                    if (!ObjectUtils.isEmpty(uniqueCode)) {
                        airtimeVTUPurchaseRequestAPI.setUniqueCode(uniqueCode);
                        airtimeVTUPurchaseRequestAPI.setDetails(Details.builder()
                                .amount(userPurchaseAirtimeVTURequest.amount())
                                .phoneNumber(userPurchaseAirtimeVTURequest.phoneNumber())
                                .build());

                        Map<String, String> headers = billerServiceRequestConfig.billerRequestHeaderConfig(airtimeVTUPurchaseRequestAPI);
                        AirtimeVTUResponseAPI airtimeVTUResponseAPI = (AirtimeVTUResponseAPI) vtuAirtimeRequestHandler
                                .sendNetworkPostRequest(EndpointUtils.VTU_AIRTIME_URL, airtimeVTUPurchaseRequestAPI, AirtimeVTUResponseAPI.class, headers);

                        AirtimeVTUTransaction airtimeVTUTransaction = AirtimeVTUTransaction.builder()
                                .amount(airtimeVTUPurchaseRequestAPI.getDetails().getAmount())
                                .phoneNumber(airtimeVTUPurchaseRequestAPI.getDetails().getPhoneNumber())
                                .user(user)
                                .build();

                        if (!ObjectUtils.isEmpty(airtimeVTUResponseAPI)) {
                            if (airtimeVTUResponseAPI.getResponseCode().equals(ResponseCode.BILLER_SUCCESS_CODE)) {
                                airtimeVTUTransaction.setTransactionStatus(VTUTransactionStatus.SUCCESSFUL);
                            } else {
                                airtimeVTUTransaction.setTransactionStatus(VTUTransactionStatus.FAILED);

                            }
                            airtimeVtuTransactionRepository.save(airtimeVTUTransaction);

                            PurchaseAirtimeResponse purchaseAirtimeResponse = PurchaseAirtimeResponse.builder()
                                    .amount(airtimeVTUTransaction.getAmount())
                                    .phoneNumber(airtimeVTUTransaction.getPhoneNumber())
                                    .transactionStatus(airtimeVTUTransaction.getTransactionStatus().toString())
                                    .build();

                            return new APIResponse<>(purchaseAirtimeResponse);
                        } else {
                            throw new BillersRequestException("Invalid response from biller airtime service");
                        }
                    } else {
                        throw new BillersRequestException("Unique code does not exist for provider");
                    }
                } else {
                    throw new BillersRequestException("Invalid network provider for airtime transaction");
                }
            } else {
                throw new UsernameNotFoundException("User does not exist");
            }
        }catch (Exception e) {
            throw new BillersRequestException("Error purchasing airtime: " + e.getMessage());
        }
    }
}
