package com.codeking.gamificationservice.event.receive;

import com.codeking.gamificationservice.domain.Problem;
import com.codeking.gamificationservice.event.dto.ProblemCreatedEvent;
import com.codeking.gamificationservice.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ProblemCreatedSink.class)
public class ProblemCreatedEventListener {

    @Autowired
    private ProblemRepository problemRepository;


    @StreamListener(ProblemCreatedSink.INPUT)
    public void handleProblemCreatedEvent(ProblemCreatedEvent problemCreatedEvent) {
        Problem problem = Problem.builder()
                .problemId(problemCreatedEvent.getProblemId())
                .problemScore(problemCreatedEvent.getProblemScore())
                .build();
        problemRepository.save(problem);
    }
}
