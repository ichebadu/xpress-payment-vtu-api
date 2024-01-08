package com.iche.xpresspayapi.service.tokenService;


import com.iche.xpresspayapi.dto.response.APIResponse;
import com.iche.xpresspayapi.exceptions.InvalidCredentialsException;
import com.iche.xpresspayapi.exceptions.OtpException;
import com.iche.xpresspayapi.exceptions.UserNotFoundException;
import com.iche.xpresspayapi.model.Token;
import com.iche.xpresspayapi.model.Users;
import com.iche.xpresspayapi.notificationEvent.registrationEvent.UserRegistrationEvent;
import com.iche.xpresspayapi.repository.TokenRepository;
import com.iche.xpresspayapi.repository.UserRepository;
import com.iche.xpresspayapi.utils.RandomGeneratedValue;
import com.iche.xpresspayapi.utils.UserVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService{
    private final TokenRepository confirmationTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserVerification userVerification;
    private final UserRepository userRepository;

    @Override
    public APIResponse<String> verifyUserOtp(String email, String otp) {
        Users user = userVerification.verifyUserByEmail(email);

        log.info("Verifying OTP: " + user.getEmail());
        Token confirmationTokenConfirmation = confirmationTokenRepository.findByUser_EmailAndOtp(user.getEmail(), otp);
        System.out.println(confirmationTokenConfirmation);

        if (confirmationTokenConfirmation == null && isOtpExpired(confirmationTokenConfirmation)) {
            throw new InvalidCredentialsException("invalid or Expired credential");

        }
        log.info(confirmationTokenConfirmation.getUser().toString());
        user.setStatus(true);
        userRepository.save(user);
        return new APIResponse<>("activate");

    }
    @Override
    public void sendOtp(Users user, String otp, Token newConfirmationToken){
        Token foundConfirmationToken = confirmationTokenRepository.findByUserId(user.getId());

        if(foundConfirmationToken != null){
            confirmationTokenRepository.delete(foundConfirmationToken);
        }
        confirmationTokenRepository.save(newConfirmationToken);
        log.info(otp);
        applicationEventPublisher.publishEvent(new UserRegistrationEvent(user,otp));
    }

    @Override
    public boolean isOtpExpired(Token confirmationToken){
        LocalDateTime otpCreatedAt = confirmationToken.getExpiresAt();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(otpCreatedAt, currentDateTime);
        long minutesPassed = duration.toMinutes();
        long otpExpiresAt = 4;
        return minutesPassed > otpExpiresAt;
    }
    @Override
    public void saveOtp(Token confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
    @Override
    public Token generateOtp(Users user) {
        String otp = RandomGeneratedValue.generateRandomValues();
        return new Token(otp, user);
    }
    @Override
    public String verifyToken(String token){
        Token confirmationToken = confirmationTokenRepository.findByOtp(token)
                .orElseThrow(()-> new UserNotFoundException("Invalid Credential"));
        if(isOtpExpired(confirmationToken)){
            confirmationTokenRepository.delete(confirmationToken);
            throw new OtpException("OTP is Expired");
        }
        return confirmationToken.getUser().getEmail();
    }
}
