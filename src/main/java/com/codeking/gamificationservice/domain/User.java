package com.codeking.gamificationservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Document(collection = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class User {

    @Id
    private String userId;

    private String userName;

    private Set<Badge> badges;

    @Indexed
    private int score;


    private Map<ProgrammingLanguage, Set<String>> programmingLanguageToSolvedProblems;


    public User() {
        badges = Collections.emptySet();
        programmingLanguageToSolvedProblems = EnumSet.allOf(ProgrammingLanguage.class).stream()
                .collect(Collectors.toMap(Function.identity(), language -> new HashSet<>()));
    }

    public User(String userId) {
        this();
        this.userId = userId;
    }

    public User(String userId, String userName) {
        this();
        this.userId = userId;
        this.userName = userName;
    }
}
