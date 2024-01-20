package com.iche.xpresspayapi.seeder;

import com.iche.xpresspayapi.enums.NetworkProviderType;
import com.iche.xpresspayapi.model.NetworkProvider;
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
public class NetworkProviderDB implements CommandLineRunner {

    private final NetworkProviderRepository networkProviderRepository;

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> uniqueCode = new HashMap<>();
        uniqueCode.put("MTN", "MTN_24207");
        uniqueCode.put("GLO", "GLO_14207");
        uniqueCode.put("ETISALAT", "ETISALAT_54207");

        for (NetworkProviderType networkProviderType : NetworkProviderType.values()) {
            String providerName = networkProviderType.toString();
            String providerCode = uniqueCode.get(providerName);


            Optional<NetworkProvider> existingProvider = networkProviderRepository.findNetworkProviderByUniqueCode(providerCode);

            if (!existingProvider.isPresent()) {
                NetworkProvider networkProvider = NetworkProvider.builder()
                        .uniqueCode(providerCode)
                        .networkProviderName(providerName)
                        .networkProviderType(networkProviderType)
                        .build();
                networkProviderRepository.save(networkProvider);
            }
        }
    }
}
