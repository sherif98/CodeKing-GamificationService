package com.codeking.gamificationservice.event.receive;


import com.codeking.gamificationservice.domain.ProgrammingLanguage;
import com.codeking.gamificationservice.domain.User;
import com.codeking.gamificationservice.event.dto.UserCreatedEvent;
import com.codeking.gamificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@EnableBinding(UserCreatedSink.class)
public class UserCreatedEventListener {

    @Autowired
    private UserRepository userRepository;

    @StreamListener(UserCreatedSink.INPUT)
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        userRepository.save(createNewUser(userCreatedEvent.getUserId(), userCreatedEvent.getUserName()));
    }

    private User createNewUser(String userId, String userName) {
        Map<ProgrammingLanguage, Set<String>> programmingLanguageToSolvedProblems =
                EnumSet.allOf(ProgrammingLanguage.class)
                        .stream()
                        .collect(Collectors.toMap(Function.identity(), language -> Collections.emptySet()));
        return User.builder()
                .userId(userId)
                .userName(userName)
                .badges(Collections.emptySet())
                .programmingLanguageToSolvedProblems(programmingLanguageToSolvedProblems)
                .build();
    }
}
