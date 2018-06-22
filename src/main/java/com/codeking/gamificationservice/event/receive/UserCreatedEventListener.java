package com.codeking.gamificationservice.event.receive;


import com.codeking.gamificationservice.domain.User;
import com.codeking.gamificationservice.event.dto.UserCreatedEvent;
import com.codeking.gamificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(UserCreatedSink.class)
public class UserCreatedEventListener {

    @Autowired
    private UserRepository userRepository;

    @StreamListener(UserCreatedSink.INPUT)
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        User user = userRepository.findById(userCreatedEvent.getUserId())
                .orElseGet(() -> new User(userCreatedEvent.getUserId(), userCreatedEvent.getUserName()));
        user.setUserName(userCreatedEvent.getUserName());
        userRepository.save(user);
    }
}
