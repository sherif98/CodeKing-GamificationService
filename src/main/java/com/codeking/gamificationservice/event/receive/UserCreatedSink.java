package com.codeking.gamificationservice.event.receive;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserCreatedSink {


    String INPUT = "userCreated";

    @Input("userCreated")
    SubscribableChannel userCreatedChannel();

}
