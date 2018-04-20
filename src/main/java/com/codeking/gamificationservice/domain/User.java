package com.codeking.gamificationservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Document(collection = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    private String userId;

    private Set<Badge> badges;

    @Indexed
    private int score;


    private Map<ProgrammingLanguage, Set<String>> programmingLanguageToSolvedProblems;
}
