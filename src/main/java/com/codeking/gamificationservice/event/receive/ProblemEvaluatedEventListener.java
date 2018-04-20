package com.codeking.gamificationservice.event.receive;

import com.codeking.gamificationservice.event.dto.ProblemEvaluatedEvent;
import com.codeking.gamificationservice.service.api.GameService;
import com.codeking.gamificationservice.service.dto.UserAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ProblemEvaluatedSink.class)
public class ProblemEvaluatedEventListener {

    @Autowired
    private GameService gameService;

    @StreamListener(ProblemEvaluatedSink.INPUT)
    public void handleProblemEvaluatedEvent(ProblemEvaluatedEvent problemEvaluatedEvent) {
        UserAttempt userAttempt = UserAttempt.builder()
                .problemId(problemEvaluatedEvent.getProblemId())
                .userId(problemEvaluatedEvent.getUserId())
                .programmingLanguage(problemEvaluatedEvent.getProgrammingLanguage())
                .submissionStatus(problemEvaluatedEvent.getSubmissionStatus())
                .build();
        gameService.processUserAttempt(userAttempt);
    }
}
