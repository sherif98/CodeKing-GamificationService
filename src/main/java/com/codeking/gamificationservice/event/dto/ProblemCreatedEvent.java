package com.codeking.gamificationservice.event.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemCreatedEvent {

    private String problemId;

    private int problemScore;
}
