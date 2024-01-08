package com.iche.xpresspayapi.repository;

import com.iche.xpresspayapi.model.NetworkProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NetworkProviderRepository extends JpaRepository<NetworkProvider, Long> {
    NetworkProvider findNetworkProviderByNetworkProviderNameEqualsIgnoreCase(String networkProviderName);
    Optional<NetworkProvider> findNetworkProviderByUniqueCode(String uniqueCode);
}
