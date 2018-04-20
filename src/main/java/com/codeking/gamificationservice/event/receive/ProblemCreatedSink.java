package com.codeking.gamificationservice.event.receive;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProblemCreatedSink {

    String INPUT = "problemCreated";

    @Input("problemCreated")
    SubscribableChannel problemCreatedChannel();
}
