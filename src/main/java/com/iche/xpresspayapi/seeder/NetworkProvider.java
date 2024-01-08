package com.iche.xpresspayapi.seeder;

import com.iche.xpresspayapi.enums.NetworkProviderType;
import com.iche.xpresspayapi.repository.NetworkProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class NetworkProvider implements CommandLineRunner {

    private final NetworkProviderRepository networkProviderRepository;

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> uniqueCode = new HashMap<>();
        uniqueCode.put("MTN", "MTN_24207");

        for (NetworkProviderType networkProviderType : NetworkProviderType.values()) {
            String providerName = networkProviderType.toString();
            String providerCode = uniqueCode.get(providerName);


            Optional<com.iche.xpresspayapi.model.NetworkProvider> existingProvider = networkProviderRepository.findNetworkProviderByUniqueCode(providerCode);

            if (existingProvider.isPresent()) {

                log.info("NetworkProvider with uniqueCode " + providerCode + " already exists.");
            } else {

                com.iche.xpresspayapi.model.NetworkProvider networkProvider = com.iche.xpresspayapi.model.NetworkProvider.builder()
                        .uniqueCode(providerCode)
                        .networkProviderName(providerName)
                        .networkProviderType(networkProviderType)
                        .build();
                networkProviderRepository.save(networkProvider);
            }
        }
    }
}
