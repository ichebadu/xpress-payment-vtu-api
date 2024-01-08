package com.iche.xpresspayapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "create_date", nullable = false )
    private LocalDateTime expiresAt;
    @Column(name = "otp",unique = true)
    private String otp;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
    )
    private Users user;
    public Token(String otp, Users user){
        this.otp = otp;
        this.user =user;
        this.expiresAt = expiresAt();
    }
    public LocalDateTime expiresAt(){
        return LocalDateTime.now().plusMinutes(5);
    }

}
