package com.iche.xpresspayapi.model;

import com.iche.xpresspayapi.enums.NetworkProviderType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "network_provider")
public class NetworkProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "provider_name")
    private String networkProviderName;
    @Enumerated(EnumType.STRING)
    private NetworkProviderType networkProviderType;
    @Column(name = "unique_code")
    private String uniqueCode;
}
