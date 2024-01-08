package com.iche.xpresspayapi.model;

import com.iche.xpresspayapi.enums.VTUTransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "airtime_transaction_tbl")
public class AirtimeVTUTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private VTUTransactionStatus transactionStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
