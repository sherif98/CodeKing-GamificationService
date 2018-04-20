package com.codeking.gamificationservice.event.dto;

import com.codeking.gamificationservice.domain.ProgrammingLanguage;
import com.codeking.gamificationservice.domain.SubmissionStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemEvaluatedEvent {

    private String problemId;
    private String userId;
    private SubmissionStatus submissionStatus;
    private ProgrammingLanguage programmingLanguage;
}
