package com.codeking.gamificationservice.event.receive;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProblemEvaluatedSink {

    String INPUT = "problemEvaluated";

    @Input("problemEvaluated")
    SubscribableChannel problemEvaluatedChannel();
}
