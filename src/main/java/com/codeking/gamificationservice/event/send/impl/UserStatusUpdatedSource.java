package com.codeking.gamificationservice.event.send.impl;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserStatusUpdatedSource {

    @Output
    MessageChannel userStatusUpdated();

}
