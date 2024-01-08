package com.iche.xpresspayapi.repository;

import com.iche.xpresspayapi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByUser_EmailAndOtp(String email, String otp);
    Token findByUserId(Long id);
    Optional<Token> findByOtp(String token);
}
