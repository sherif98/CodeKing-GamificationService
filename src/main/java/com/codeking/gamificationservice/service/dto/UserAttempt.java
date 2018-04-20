package com.codeking.gamificationservice.service.dto;

import com.codeking.gamificationservice.domain.ProgrammingLanguage;
import com.codeking.gamificationservice.domain.SubmissionStatus;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserAttempt {

    private String problemId;
    private String userId;
    private SubmissionStatus submissionStatus;
    private ProgrammingLanguage programmingLanguage;
}
