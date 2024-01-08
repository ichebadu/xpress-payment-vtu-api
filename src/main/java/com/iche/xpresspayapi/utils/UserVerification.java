package com.iche.xpresspayapi.utils;

import com.iche.xpresspayapi.exceptions.UserNotFoundException;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
public class UserVerification {
    private final UserRepository userRepository;

    public Users verifyUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not Found"));
    }
    public static String getUserEmailFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
