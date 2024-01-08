package com.iche.xpresspayapi.notificationEvent.registrationEvent;



import com.iche.xpresspayapi.model.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private Users user;
    private String otp;

    public UserRegistrationEvent(Users user, String otp) {
        super(user);
        this.user = user;
        this.otp = otp;
    }
}
