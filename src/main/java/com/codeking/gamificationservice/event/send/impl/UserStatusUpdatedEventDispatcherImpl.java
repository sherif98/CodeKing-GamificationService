package com.codeking.gamificationservice.event.send.impl;

import com.codeking.gamificationservice.event.dto.UserStatusUpdatedEvent;
import com.codeking.gamificationservice.event.send.api.UserStatusUpdatedEventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;


@EnableBinding(UserStatusUpdatedSource.class)
public class UserStatusUpdatedEventDispatcherImpl implements UserStatusUpdatedEventDispatcher {

    @Autowired
    private UserStatusUpdatedSource userStatusUpdatedSource;

    @Override
    public void dispatchUserStatusUpdatedEvent(UserStatusUpdatedEvent userStatusUpdatedEvent) {
        Message<UserStatusUpdatedEvent> userStatusUpdatedEventMessage = MessageBuilder.withPayload(userStatusUpdatedEvent).build();
        userStatusUpdatedSource.userStatusUpdated().send(userStatusUpdatedEventMessage);
    }
}
